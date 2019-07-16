(ns global-affairs-sparkling.core
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [global-affairs-sparkling.postgres-sink :as pg]
            [sparkling.destructuring :as dest]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [clojure.string :as string]

            ;[amazonica.aws.s3 :as s3]
            ;[amazonica.core :as aws]
            ;[amazonica.aws.s3transfer :as s3transfer]
            ;[amazonica.aws.ec2 :as ec2]
            ;[global-affairs-sparkling.s3connect :as s3c]
            [global-affairs-sparkling.sparkprocessing :as sp]
            [sparkling.serialization :as requirered-to-have-serializer-class-ready]
            [clojure.data.csv :as csv])
  (:gen-class)
  ;(:import org.apache.spark.api.java.JavaUtils SerializableMapWrapper)
  )
;;(:import  [org.apache.hadoop hadoop-aws]
;;          [org.apache.hadoop.fs.s3a S3AFileSystem AWSCredentialProviderList])
;;          org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
;;          org.apache.spark.SparkContext
;;          [org.apache.spark.streaming StreamingContext Duration]
;;)

(def local-ip (env :spark-local-ip))
;;Change these to (System/getProperty access-key)

(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(System/setProperty "spark.serializer" "org.apache.spark.serializer.KryoSerializer")
(System/setProperty "spark.kryo.registrator" "sparkling.serialization.BaseRegistrator")



;;project will import s3 csv information to be processed by spark

;;use s3 service to connect to s3, get a list of the buckets and create an s3 url to be used by spark process

;;call spark to read the data from each csv on a node.
;;have each node process the data using transformation funtions, maps and expanding and aggregating data

;;reduce the data to a smaller set to be uploaded to DB (postgres)

;;use tableau to pull transformed data from db
;;collect the counts of the KV: keys and the averages

;;reduce the values to get their sums and avg and add MEPV possibly SFI values



(defn make-spark-context []
  (let [conf-gsc (-> (conf/spark-conf)
                     (conf/master "local[*]")
                     (conf/app-name "global-affairs") )]
                     ;; (conf/set "fs.s3a.connection.timeout" "200000")
                     ;; (conf/set "spark.akka.timeout" "300")
    (spark/spark-context conf-gsc)))


(defn -main [& args]
  (let [gsc (make-spark-context)
        link "s3a://gdelt-open-data"
        gdelt-bucket "v2/events/"
        gdelt-link  "s3a://gdelt-open-data/v2/events/"
        file-link-dir "s3a://gdeltdata/"
        ;;file-list s3c/get-gdelt-list
        ]
      (println gsc)
      (try
        (->> gdelt-link ;;file-link-dir
             (spark/whole-text-files  gsc)
             (spark/flat-map-values (fn [line] (string/split line #"\n")))
             (spark/map-to-pair (dest/key-value-fn (fn [k v]
                                                     (sp/mk-datecountryevent-tuple v))))
             (spark/reduce-by-key +)
             ;;(spark/filter );delete nulls
             (spark/map (dest/key-value-fn (fn [k v]
                                             (sp/convert-key-code-vector k v))))
             (spark/glom)
             (spark/foreach-partition #(pg/dne-table-insert (first %)))

             ;;(println "----------------------pprint---------------------------------------------")                           ;
             )
        (catch Exception e
          (spark/stop gsc)
          (str "caught exception: " (.getMessage e))))
      gsc))
