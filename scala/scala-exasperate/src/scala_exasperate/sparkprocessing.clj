(ns scala-exasperate.sparkprocessing
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]])
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
(def conf-gsc (-> (conf/spark-conf)
                  (conf/master );master-url
                  (conf/app-name gdelt-app-name)
                  ;; (conf/set "fs.s3n.awsAccessKeyId" (env :aws-access-key))
                  ;; (conf/set "fs.s3n.awsSecretAccessKey"  (env :aws-secret-key))
                  ))

;;sc.textFile("s3n://MyAccessKeyID:MySecretKey@zpub01/SafeAndSound_Lyrics.txt")
(def s3nlink "s3n://gdelt-open-data")

;;https://stackoverflow.com/questions/47528322/failed-to-register-classes-with-kryo
;; AOT compilation of deseralization is required because some of the dependencies this could also potentially solve it
;(conf/jars (map #(.getPath % (.getURLs(java.lang.ClassLoader/getSystemClassLoader)))))(conf/jars conf-gsc )

(def gsc (sparkling.core/spark-context conf-gsc))

;;import data from a s3url link
(def gdelt-data ;(spark/text-file gsc s3nlink))
  (let [s3nlink "s3n://gdelt-open-data"
        temp-bucket "v2/events/20150328170000.export.csv"
        file-link (str s3nlink "/" temp-bucket)
        file-link2 (str "s3n://" "gdeltdata" "20190613211500.export.CSV")
        file-local "20190613211500.export.csv"]
    (spark/text-file gsc file-local)))

(->> gdelt-data (spark/map count) (spark/reduce +))
(clojure.string/split (spark/first gdelt-data) #"\t")

;;gets rdd splits values into a vector
(->>  gdelt-data
      (spark/first)
      ((fn  [line] (clojure.string/split line #"\t")));add (map)
      ((fn ))


;;;;;(defn process)

;;process the csv file and filter out only data necessary for graphs

;;expand data to create new keys from column header and values to aggregate adding one count

;;collect the counts of the KV: keys and the averages

;;reduce the values to get their sums and avg and add MEPV possibly SFI values
