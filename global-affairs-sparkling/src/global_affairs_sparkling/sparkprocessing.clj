(ns global-affairs-sparkling.sparkprocessing
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [global-affairs-sparkling.data-conversion :as convert]
            [environ.core :refer [env]]
            [clojure.string :as string]
            [sparkling.utils :as utils]
            [clj-time.core :as time]
            [clj-time.coerce :as t-coerce]))

(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(def local-ip (env :spark-local-ip))
(def master-url "local")


;;https://stackoverflow.com/questions/47528322/failed-to-register-classes-with-kryo
;; AOT compilation of deseralization is required because some of the dependencies this could also potentially solve it


;;process the csv file and filter out only data necessary for graphs


(defn convert-country-code
  "returns a conversion of the country_code_key: passed. A 2-char key returns the string name of the country. A three-char-key: returns a 2-char-key:"
  [code]
  (get convert/country-codes-hashmap code))

(defn convert-event-code
  [code-string]
  (get convert/country-codes-hashmap (keyword code-string)))

(defn csv-line-to-vector
  "Retuns a string vector converting the tab delimited string passed"
  [line]
  (string/split line #"\t"))

;;EventRootCode to granular
;;columns that will be extracted from csv line
(def g-columns-to-get ["MonthYear"
                       "Actor1CountryCode"
                       "Actor2CountryCode"
                       "EventCode"
                       "GoldsteinScale"
                       "Actor1Geo_CountryCode"
                       "Actor2Geo_CountryCode"
                       "ActionGeo_CountryCode"])

(defn get-columns-from-line
  "returns a collection of values from the column_name_vector. It expects a hashmap of the {columns-name and index} and a vector of [column-names*]"
  [column-name-map  names-of-columns-to-get  row-line]
  (let [column->index column-name-map
        indices (->> names-of-columns-to-get
                     (mapv column->index)
                     (set))]
       (keep-indexed (fn [i v] (if (indices i) v)) row-line)))

;;Returns 
(def get-columns #(get-columns-from-line convert/gdelt-columns-hashmap g-columns-to-get  %))

;;Returns a map of the rows of data with their corresponding assigned keys
;;Has to match order from g-columns-to-get fn
(def tag-gdelt-columns #(zipmap [:date :ac1 :ac2 :ec :gsc :gc1 :gc2 :egc] %))

(defn convert-3-letter-country-codes
  "Returns a matching country code or country names, takes a vector of country code strings"
  [two-nation-codes]
  (map #(if (and (= 3 (count %)) (not (nil? %))) 
          (-> %
              keyword
              convert-country-code
              )
          (keyword %))
       two-nation-codes))

(defn get-national-codes
  "returns a collection of country code keys, A 'row' collection of strings is passed and the codes for the nations involed gets returned"
  [row-string]
  (convert-3-letter-country-codes
   (let [row (tag-gdelt-columns row-string);;refactor out 
         vals-of-selected-keys #(vals (select-keys row %))
         vals-of-selected-keys-in-vector #(vals (select-keys %2 %1));first row[] then [keys]
         no-blanks-in-vector? #(if (> (count %) 1)
                                 (not-any? string/blank? %)
                                 false)
         do-these-tags-exist-in-row? #(no-blanks-in-vector? (vals-of-selected-keys-in-vector %2 %1))]
     (cond
       (do-these-tags-exist-in-row? row [:ac1 :ac2]) (vals-of-selected-keys [:ac1 :egc])
       (do-these-tags-exist-in-row? row [:ac1 :gc2]) (vals-of-selected-keys [:ac1 :gc2])

       (do-these-tags-exist-in-row? row [:gc1 :ac2]) (vals-of-selected-keys [:gc1 :ac2])
       (do-these-tags-exist-in-row? row [:gc1 :gc2]) (vals-of-selected-keys [:gc1 :gc2])

       (do-these-tags-exist-in-row? row [:gc1 :egc]) (vals-of-selected-keys [:gc1 :egc])
       (do-these-tags-exist-in-row? row [:ac1 :egc]) (vals-of-selected-keys [:ac1 :egc])

       (do-these-tags-exist-in-row? row [:gc2 :egc]) (vals-of-selected-keys [:gc2 :egc])
       (do-these-tags-exist-in-row? row [:ac2 :egc]) (vals-of-selected-keys [:ac2 :egc])
                                        ;no country codes found
       :else (vals-of-selected-keys  [:egc :egc])))))


(def get-national-codes-to-keys #(->> %
                                     get-national-codes
                                     (map keyword)))

(defn country-codes-to-str
  "Returns a list of country codes in string format, expects a tuple of two keys"
  [code-vector]
  (if (not-any? nil? code-vector)
    (map name code-vector)
    ["null" "null"]
    ))


(defn flatten-event-code
  "Returns a max 3 digit code if 4"
  [code]
  (if (= 5 (count code))
    (subs code 0 3)
    code))

(defn mk-datecountryevent-key
  "returns a string key concatenated from date country_code1 country_code2, and event_code. Expects a map of the values from row and a vector of two country codes"
  [{:keys [date ec] :as row}  countries-code-vector]
  (let [countries (country-codes-to-str countries-code-vector)
        key-date-co-code (flatten [date countries (flatten-event-code ec)])]
    (clojure.string/join "_" key-date-co-code)))


;;expand data to create new keys from column header and values to aggregate adding one count
(defn mk-datecountryevent-tuple
  "Returns a spark tuple composed of the relational key based on the row and a value of 1 to account for that event line"
  [csv-row-string]
  (let [row-split-vector (csv-line-to-vector csv-row-string)
        selected-columns (get-columns row-split-vector)
        national-codes (get-national-codes selected-columns)
        column-map (tag-gdelt-columns selected-columns)
        relationship-key (mk-datecountryevent-key column-map national-codes)]
    (spark/tuple relationship-key 1)))

(defn convert-key-code-vector
  "Returns a vector of strings from the id string passed and converts it for insert in table"
  [id-string summed-val]
  (let [id-string-vector (clojure.string/split id-string #"_")
        [date country-code1 country-code2 event-code] id-string-vector]
    [id-string
     (t-coerce/to-sql-date date)
     (convert-country-code (keyword country-code1))
     (convert-country-code (keyword country-code2))
     event-code ;(flatten-event-code event-code)
     (convert-event-code event-code)
     summed-val]))
