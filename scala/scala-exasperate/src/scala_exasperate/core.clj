(ns scala-exasperate.core
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3transfer]
            [amazonica.aws.ec2 :as ec2])
  (:import [org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
           org.apache.spark.SparkContext
           [org.apache.spark.streaming StreamingContext Duration]))


(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})

(def local-ip (env :spark-local-ip))



