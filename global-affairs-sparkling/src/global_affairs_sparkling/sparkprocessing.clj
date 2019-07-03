(ns global-affairs-sparkling.sparkprocessing
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [global-affairs-sparkling.data-conversion :as convert]
            [environ.core :refer [env]]
            [clojure.string :as string]
            [sparkling.utils :as utils])
 ; (:import [org.apache.hadoop.fs.FileSystem])
  )
;; (:import [org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
;;          org.apache.spark.SparkContext
;;          [org.apache.spark.streaming StreamingContext Duration]
;;          [org.apache.hadoop.fs.s3 S3FileSystem S3FileSystemConfigKeys]
;;          )

;;Change these to (System/getProperty access-key)
(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(def local-ip (env :spark-local-ip))
(def master-url "local")
(def gdelt-app-name "gdelt-processor")

;(def s3nlink "s3n://gdelt-open-data")
(def temp-bucket "v2/events/20150328170000.export.csv")

;;initialize spark configuration
;; (def conf-gsc (-> (conf/spark-conf)
;;                   (conf/master );master-url
;;                   (conf/app-name gdelt-app-name)
;;                   ;; (conf/set "fs.s3n.awsAccessKeyId" (env :aws-access-key))
;;                   ;; (conf/set "fs.s3n.awsSecretAccessKey"  (env :aws-secret-key))
;;                   ))
;;sparkling?
;;gdelt-context
;;;;;;;;;;;;;;;;;;;;;;;;
                                        ;;;;;;;;;;;;;;;;;;;;;;;;;;;(def gsc (spark/spark-context conf-gsc))

;;sc.textFile("s3n://MyAccessKeyID:MySecretKey@zpub01/SafeAndSound_Lyrics.txt")
(def s3alink "s3a://gdelt-open-data")

;;https://stackoverflow.com/questions/47528322/failed-to-register-classes-with-kryo
;; AOT compilation of deseralization is required because some of the dependencies this could also potentially solve it
;(conf/jars (map #(.getPath % (.getURLs(java.lang.ClassLoader/getSystemClassLoader)))))(conf/jars conf-gsc )

;;import data from a s3url link
;; (def gdelt-data ;(spark/text-file gsc s3nlink))
;;   (let [s3nlink "s3n://gdelt-open-data"
;;         temp-bucket "v2/events/20150328170000.export.csv"
;;         file-link (str s3nlink "/" temp-bucket)
;;         file-link2 (str "s3n://" "gdeltdata" "/20190613211500.export.CSV")
;;         file-local "20190613211500.export.csv"]
;;     (spark/text-file gsc file-local)))


;;;;;;TESTING ----------------------------------------------------------------TEST
;; (->> gdelt-data (spark/map count) (spark/reduce +));count
;; (def gdelt-first-line (string/split (spark/first gdelt-data) #"\t"))


;;process the csv file and filter out only data necessary for graphs


(defn convert-country-code
  "returns a conversion of the country_code_key: passed. A 2-char key returns the string name of the country. A three-char-key: returns a 2-char-key:"
  [code]
  (get convert/country-codes-hashmap code))


(defn csv-line-to-vector
  "converts a tab delimited string into a string vector"
  [line]
  (string/split line #"\t"))

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
  ;(spark/map---------------------------------------------------------
  (let [column->index column-name-map
        indices (->> names-of-columns-to-get
                     (mapv column->index)
                     (set))]
       (keep-indexed (fn [i v] (if (indices i) v)) row-line)))

(def get-columns #(get-columns-from-line convert/gdelt-columns-hashmap g-columns-to-get  %))

;; (->> gdelt-data
;;      (spark/map csv-line-vector)
;;      (spark/map get-columns)
;;      (spark/first))

;;;;;;;TEST
;(get-columns gdelt-first-line)
;;output ("201806" "" "" "061" "6.4" "" "MX" "")


(def tag-gdelt-columns #(zipmap [:date :ac1 :ac2 :ec :gsc :gc1 :gc2 :egc] %))
;;TEST
;(-> gdelt-first-line get-columns tag-gdelt-columns)
;{:date "201806", :ac1 "", :ac2 "", :ec "061", :gsc "6.4", :gc1 "", :gc2 "MX", :egc "US"}

(defn convert-3-letter-country-codes
  "takes a vector of country code strings"
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
         no-blanks-in-vector? #(not-any? string/blank? %)
         do-these-tags-exist-in-row? #(no-blanks-in-vector? (vals-of-selected-keys-in-vector %2 %1))]
     (cond
       (do-these-tags-exist-in-row? row [:ac1 :ac2]) (vals-of-selected-keys [:ac1 :egc])
       (do-these-tags-exist-in-row? row [:ac1 :gc2]) (vals-of-selected-keys [:ac1 :gc2])

       (do-these-tags-exist-in-row? row [:gc1 :ac2]) (vals-of-selected-keys [:gc1 :ac2])
       (do-these-tags-exist-in-row? row [:gc1 :gc2]) (vals-of-selected-keys  [:gc1 :gc2])

       (do-these-tags-exist-in-row? row [:gc1 :egc]) (vals-of-selected-keys  [:gc1 :egc])
       (do-these-tags-exist-in-row? row [:ac1 :egc]) (vals-of-selected-keys  [:ac1 :egc])

       (do-these-tags-exist-in-row? row [:gc2 :egc]) (vals-of-selected-keys  [:gc2 :egc])
       (do-these-tags-exist-in-row? row [:ac2 :egc]) (vals-of-selected-keys  [:ac2 :egc])
                                        ;no country codes found
       :else nil))
   ))

;;;TEST-------------------
;(-> gdelt-first-line get-columns get-national-codes)
;;output("MX" "US")

;;I don't think I want this
(def get-national-codes-to-keys #(->> %
                                     get-national-codes
                                     (map keyword)))

;;expand data to create new keys from column header and values to aggregate adding one count

(defn mk-datecountryevent-key
  "returns a string key concatenated from date country_code1 country_code2, and event_code. Expects a map of the values from row and a vector of two country codes"
  [{:keys [date ec] :as row}  countries-code-vector]
  (let [countries (map name countries-code-vector)
        key-date-co-code (flatten [date (map name countries-code-vector) ec])]
    (apply str key-date-co-code)))

;; (defn get-row-data
;;   [row-string])

;;TEST Assure mapping order is correct
;(->> [:date :egc :gc2 :ec] (select-keys tmp) vals (apply str))
;;> "201806USMX061"
;; (defn mk-key [date c1 c2 ec]
;;   (str date c1 c2 ec))

(defn mk-datecountryevent-tuple [csv-row-string]
  (let [row-split-vector (csv-line-to-vector csv-row-string)
        selected-columns (get-columns row-split-vector)
        national-codes (get-national-codes selected-columns)
        column-map (tag-gdelt-columns selected-columns)
        relationship-key (mk-datecountryevent-key column-map national-codes)]
    (spark/tuple relationship-key 1)))

;; (spark/map-to-pair
;;  (fn [[_ _ total _ :as row]]
;;    (spark/tuple total row)))





;;collect the counts of the KV: keys and the averages

;;reduce the values to get their sums and avg and add MEPV possibly SFI values


;
