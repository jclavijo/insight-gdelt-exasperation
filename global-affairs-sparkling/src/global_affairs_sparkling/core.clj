(ns global-affairs-sparkling.core
  (:require [global-affairs-sparkling.sparkprocessing :as sp]
            [clojure.data.csv :as csv]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [global-affairs-sparkling.postgres-sink :as pg]
            [sparkling.conf :as conf]
            [sparkling.core :as spark]
            [sparkling.destructuring :as dest]
            [sparkling.serialization :as requirered-to-have-serializer-class-ready])
  (:gen-class))

(def master-ip (env :spark-local-ip))
;;Change these to (System/getProperty access-key)

(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(System/setProperty "spark.serializer" "org.apache.spark.serializer.KryoSerializer")
(System/setProperty "spark.kryo.registrator" "sparkling.serialization.BaseRegistrator")

;;use s3 service to connect to s3, get a list of the buckets and create an s3 url to be used by spark process


(defn make-spark-context []
  (let [conf-gsc (-> (conf/spark-conf)
                     (conf/master "spark://10.0.0.45:7077")  ;(env :spark-local-ip))
                     ;; (conf/master "local[*]")
                     (conf/app-name "global-affairs"))]
                     ;; (conf/set "fs.s3a.connection.timeout" "200000")
                     ;; (conf/set "spark.akka.timeout" "300")
    (spark/spark-context conf-gsc)))


(defn insert-partition-to-db
  [rdd]
  (if (some? (spark/first rdd))
      (dorun (pg/dne-table-insert (spark/first rdd)))))
;;     (let [new-rdd-split (partition 30000 rdd)]
;;       (dorun (pmap pg/dne-table-insert new-rdd-split)))))


;;project will import s3 csv information to be processed by spark
(defn -main [& args]
  (let [gsc (make-spark-context)
        link "s3a://gdelt-open-data"
        gdelt-bucket "v2/events/"
        gdelt-link  "s3a://gdelt-open-data/v2/events/2018*.csv"
        file-link-dir "s3a://gdeltdata/"
        ;;file-list s3c/get-gdelt-list
        ]
      (try
        (->> gdelt-link
              ;;file-link-dir
             (#(spark/whole-text-files gsc % 12 ) )  ;;call spark to read the data from each csv on a node.
             (spark/flat-map-values (fn [line] (string/split line #"\n")));;have each node split files
             ;;transformation funtions, maps and expanding and aggregating data
             (spark/map-to-pair (dest/key-value-fn (fn [k v]
                                                     (sp/mk-datecountryevent-tuple v))))
            ; (spark/filter  (dest/key-value-fn (fn [k v] )                            ))
             ;;collect the counts of the KV: keys and the averages
             (spark/reduce-by-key +)
             ;;(spark/filter );delete nulls
             (spark/map (dest/key-value-fn (fn [k v]
                                             (sp/convert-key-code-vector k v))))
             ;;reduce the data to a smaller set to be uploaded to DB (postgres)
             (spark/glom)
             (spark/foreach-partition insert-partition-to-db))
        (catch Exception e
          (spark/stop gsc)
          (str "caught exception: " (.getMessage e))))
      gsc))
