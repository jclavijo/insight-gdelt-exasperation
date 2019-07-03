(ns global-affairs-sparkling.s3connect
  (:require [environ.core :refer [env]]
            [amazonica.aws.s3 :as s3]
            [amazonica.core :as aws]
            [amazonica.aws.s3transfer :as s3transfer]
            [amazonica.aws.ec2 :as ec2]))

;;Change these to (System/getProperty access-key)
(def aws-cred {:access-key (env :aws-access-key)
               :secret-key (env :aws-secret-key)
               :endpoint   (env :aws-endpoint)})
(def local-ip (env :spark-local-ip))


;;connect to s3 and get the list GDELT csv files and the MVEP

;;create a list of urls to be consumed by spark,


;; (println (ec2/describe-instances aws-cred))

;; (s3/list-buckets
 ;; {:client-config {
                  ;; :path-style-access-enabled false
                  ;; :chunked-encoding-disabled false
                  ;; :accelerate-mode-enabled false
                  ;; :payload-signing-enabled true
                  ;; :dualstack-enabled true
                  ;; :force-global-bucket-access-enabled true}})

(aws/defcredential
  (env :aws-access-key)
  (env :aws-secret-key)
  (env :aws-endpoint))

(def gdelt-cred {:access-key (env :aws-access-key)
                 :secret-key (env :aws-secret-key)
                 :endpoint "us-east-1"
                 :client-config {
                                 :path-style-access-enabled false
                                 :chunked-encoding-disabled false
                                 :accelerate-mode-enabled false
                                 :payload-signing-enabled true
                                 :dualstack-enabled true
                                 :force-global-bucket-access-enabled false
                                 :cli_follow_urlparam false 
                                 ;;:protocol "s3"
                                 }})

;(def bucketlist (s3/list-objects-v2 gdelt-cred
;                                    {:bucket-name "gdelt-open-data"}))

;(get-in bucketlist [:object-summaries 1 :key])

(def get-gdelt-list
  (let [bucket-list (-> gdelt-cred
                        (s3/list-objects-v2 {:bucket-name "gdelt-open-data"})
                        (get-in [:object-summaries])
                        rest)]
    (mapv #(get % :key) bucket-list)))

(defn get-cvs-from-s3
  "Returns a string a string of the csv file"
  [csv-key]
  (slurp (:input-stream 
          (s3/get-object gdelt-cred "gdelt-open-data" csv-key))))



;; (def csv1979 (slurp (:input-stream 
;;                      (s3/get-object gdelt-cred "gdelt-open-data" "v2/events/20150328170000.export.csv"))))
