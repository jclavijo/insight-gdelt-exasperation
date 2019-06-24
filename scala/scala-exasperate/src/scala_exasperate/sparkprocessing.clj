(ns scala-exasperate.sparkprocessing
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]])
  (:import [org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
           org.apache.spark.SparkContext
           [org.apache.spark.streaming StreamingContext Duration]))

;;Change these to (System/getProperty access-key)
(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(def local-ip (env :spark-local-ip))


;;import data from a s3url link

;;process the csv file and filter out only data necessary for graphs

;;expand data to create new keys from column header and values to aggregate adding one count

;;collect the counts of the KV: keys and the averages

;;reduce the values to get their sums and avg and add MEPV possibly SFI values
