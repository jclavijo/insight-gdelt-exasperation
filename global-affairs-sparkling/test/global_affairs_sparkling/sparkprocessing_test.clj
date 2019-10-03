(ns global-affairs-sparkling.sparkprocessing-test
  (:require [global-affairs-sparkling.sparkprocessing :as sut]
            [clojure.test :as t]))
;; (:import [org.apache.hadoop.fs.FileSystem])

;; (:import [org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
;;          org.apache.spark.SparkContext
;;          [org.apache.spark.streaming StreamingContext Duration]
;;          [org.apache.hadoop.fs.s3 S3FileSystem S3FileSystemConfigKeys]
;;          )
(def gdelt-app-name "gdelt-processor")

(def temp-bucket "v2/events/20150328170000.export.csv")

(def s3alink "s3n://gdelt-open-data")

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
;;(conf/jars (map #(.getPath % (.getURLs(java.lang.ClassLoader/getSystemClassLoader)))))(conf/jars conf-gsc )
;;import data from a s3url link
;; (def gdelt-data ;(spark/text-file gsc s3nlink))
;;   (let [s3nlink "s3n://gdelt-open-data"
;;         temp-bucket "v2/events/20150328170000.export.csv"
;;         file-link (str s3nlink "/" temp-bucket)
;;         file-link2 (str "s3n://" "gdelt data" "/20190613211500.export.CSV")
;;         file-local "20190613211500.export.csv"]
;;     (spark/text-file gsc file-local)))


;;;;;;TESTING ----------------------------------------------------------------TEST
;; (->> gdelt-data (spark/map count) (spark/reduce +));count
;; (def gdelt-first-line (string/split (spark/first gdelt-data) #"\t"))

;; (->> gdelt-data
;;      (spark/map csv-line-vector)
;;      (spark/map get-columns)
;;      (spark/first))

;;;;;;;TEST
                                        ;(get-columns gdelt-first-line)
;;output ("201806" "" "" "061" "6.4" "" "MX" "")

;;TEST
                                        ;(-> gdelt-first-line get-columns tag-gdelt-columns)
                                        ;{:date "201806", :ac1 "", :ac2 "", :ec "061", :gsc "6.4", :gc1 "", :gc2 "MX", :egc "US"}

;;;TEST-------------------
                                        ;(-> gdelt-first-line get-columns get-national-codes)
;;output("MX" "US")

;; (defn get-row-data
;;   [row-string])

;;TEST Assure mapping order is correct
                                        ;(->> [:date :egc :gc2 :ec] (select-keys tmp) vals (apply str))
;;> "201806USMX061"
;; (defn mk-key [date c1 c2 ec]
;;   (str date c1 c2 ec))

;;(def s3nlink "s3n://gdelt-open-data")
;; (let [row-str '("201806" "US" "" "061" "6.4" "" "MX" "CA")
;;       row (zipmap [:date :ac1 :ac2 :ec :gsc :gc1 :gc2 :egc] row-str)
;;       vals-of-selected-keys #(vals (select-keys row %))
;;       vals-of-selected-keys-in-vector #(vals (select-keys %2 %1));first row[] then [keys]
;;       no-blanks-in-vector? #(not-any? string/blank? %)
;;       do-these-tags-exist? #(no-blanks-in-vector? (vals-of-selected-keys %))
;;       do-these-tags-exist-in-row? #(no-blanks-in-vector? (vals-of-selected-keys-in-vector %2 %1));order because of condp
;;       ]
;;   (println row
;;            (vals-of-selected-keys [:date :ac1])
;;            (no-blanks-in-vector? '("1" ""))
;;            (do-these-tags-exist? [:date :ac1])
;;            (do-these-tags-exist? [:ac1 :ac2])
;;            (do-these-tags-exist-in-row? row [:ac1 :gc2])
;;            )
;;   ;; (if (do-these-tags-exist? [:ac1 :gc2])
;;   ;;   (vals-from-selected-keys [:ac1 :gc2])
;;   ;;   false)
;;   (cond
;;     (do-these-tags-exist-in-row? row [:ac1 :ac2]) (vals-of-selected-keys [:ac1 :egc])
;;     (do-these-tags-exist-in-row? row [:ac1 :gc2]) (vals-of-selected-keys [:ac1 :gc2])

;;     (do-these-tags-exist-in-row? row [:gc1 :ac2]) (vals-of-selected-keys [:gc1 :ac2])
;;     (do-these-tags-exist-in-row? row [:gc1 :gc2]) (vals-of-selected-keys  [:gc1 :gc2])

;;     (do-these-tags-exist-in-row? row [:ac1 :egc]) (vals-of-selected-keys  [:ac1 :egc])
;;     (do-these-tags-exist-in-row? row [:gc1 :egc]) (vals-of-selected-keys  [:gc1 :egc])
;;     :else nil)
;;   )

;;(convert-key-code-vector "201906_US_CA_011" 111)
;;["201906USCA0213" "201906" "US" "CA" "011" "Decline comment" 111]
