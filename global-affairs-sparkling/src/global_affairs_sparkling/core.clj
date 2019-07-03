(ns global-affairs-sparkling.core
  (:require [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [amazonica.aws.s3 :as s3]
            [amazonica.core :as aws]
            [amazonica.aws.s3transfer :as s3transfer]
            [amazonica.aws.ec2 :as ec2]
            [global-affairs-sparkling.sparkprocessing :as sp]
            [global-affairs-sparkling.s3connect :as s3c]
            [clojure.data.csv :as csv])
  (:gen-class)
  ;(:import ;[org.apache.hadoop hadoop-aws]
  ;;          [org.apache.hadoop.fs.s3a S3AFileSystem AWSCredentialProviderList])
            ;;org.apache.spark.streaming.api.java JavaStreamingContext JavaDStream]
           ;org.apache.spark.SparkContext
           ;;          [org.apache.spark.streaming StreamingContext Duration]
          ; )
  )

(def local-ip (env :spark-local-ip))
;;Change these to (System/getProperty access-key)
(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(System/setProperty "spark.serializer" "org.apache.spark.serializer.KryoSerializer")
(System/setProperty "spark.kryo.registrator" "sparkling.serialization.BaseRegistrator")
(System/setProperty "fs.s3a.access.key" (env :aws-access-key ))
(System/setProperty "fs.s3a.secret.key" (env :aws-secret-key)) 




;;project will import s3 csv information to be processed by spark

;;use s3 service to connect to s3, get a list of the buckets and create an s3 url to be used by spark process

;;call spark to read the data from each csv on a node.
;;have each node process the data using transformation funtions, maps and expanding and aggregating data

;;reduce the data to a smaller set to be uploaded to DB (postgres)

;;use tableau to pull transformed data from db

 ; (spark/with-context scontext conf-sc
    ;;(BasicAWSCredentialsProvider. (env :aws-access-key ) (env :aws-secret-key))
    ;;org.apache.hadoop.fs.s3a.AWSCredentialProviderList/getCredentials
                                        ;(println (p/csv-line-to-vector (spark/first (spark/text-file c file-link2))));
 

;(def data (spark/parallelize sc 4 [1 2 3 4 5]))
;; (def votes (s/parallelize-pairs sc [(s/tuple "Woody Allen" :upvote)
;;                                     (s/tuple "Woody Allen" :upvote)
;;                                     (s/tuple "Genghis Khan" :downvote)
;;                                     (s/tuple "Genghis Khan" :downvote)
                                    ;; (s/tuple "Bugs Bunny" :upvote)]))

                                        ;(read-csv input & options)

(defn make-spark-context []
  (let [conf-gsc (-> (conf/spark-conf)
                     (conf/master "local[*]")
                     (conf/app-name "global-affairs"
                                    ;; (conf/set "fs.s3a.connection.timeout" "200000")
                                    ;; (conf/set "fs.awsAccessKey"  (env :aws-access-key));(env :aws-secret-key)
                                    ;; (conf/set "fs.awsSecretAccessKey"  (env :aws-secret-key));(env :aws-secret-key)
                                    ;; (conf/set "spark.akka.timeout" "300")
                                    ;; (conf/set "fs.s3a.access.key" (env :aws-access-key))
                                    ;; (conf/set "fs.s3a.secret.key" (env :aws-secret-key))
                                    ))]
    (spark/spark-context conf-gsc)))


(defn -main [& args]
  (spark/with-context gsc (-> (conf/spark-conf)
                              (conf/master "local[*]")
                              (conf/app-name "global-affairs" ))
    (let [;gsc (make-spark-context)
          s3alink "s3a://gdelt-open-data"
          temp-bucket "v2/events/20150328170000.export.csv"
          file-link (str s3alink "/" temp-bucket)
          file-link2 "s3a://gdeltdata/20190613211500.export.csv" ;20190613211500.export.csv
          file-local "20190613211500.export.csv"
          num-of-files 1 
          file-list s3c/get-gdelt-list
          files (take-last num-of-files file-list)
          ]
      (println gsc)
      (->> files
           (spark/parallelize gsc) ;(take-last num-of-files file-list  );(dorun (s3c/get-gdelt-list))
                                        ;(into [])
           (spark/map #(s3c/get-cvs-from-s3 %))
           ;; (spark/map csv/read-csv )

           (spark/first)
           println

           ;;  ;(spark/values)
           ;;  (spark/first)
                                        ;(spark/map-to-pair sp/mk-datecountryevent-tuple)
           ))
    (spark/stop gsc)
    ))
