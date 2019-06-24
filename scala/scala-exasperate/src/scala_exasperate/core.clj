(ns scala-exasperate.core
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [amazonica.aws.s3 :as s3]
            [amazonica.core :as aws]
            [amazonica.aws.s3transfer :as s3transfer]
            [amazonica.aws.ec2 :as ec2])
  (:import [org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
           org.apache.spark.SparkContext
           [org.apache.spark.streaming StreamingContext Duration]))

;;Change these to (System/getProperty access-key)
(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(def local-ip (env :spark-local-ip))

;;project will import s3 csv information to be processed by spark

;;use s3 service to connect to s3, get a list of the buckets and create an s3 url to be used by spark process

;;call spark to read the data from each csv on a node.
;;have each node process the data using transformation funtions, maps and expanding and aggregating data

;;reduce the data to a smaller set to be uploaded to DB (postgres)

;;use tableau to pull transformed data from db
