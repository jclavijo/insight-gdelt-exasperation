(ns scala-exasperate.s3connect
  (:require 
            [environ.core :refer [env]]
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

;;create a list of urls to be consumed by spark


;(println (ec2/describe-instances aws-cred))

;(aws/)

(s3/list-buckets
 {:client-config {
                  :path-style-access-enabled false
                  :chunked-encoding-disabled false
                  :accelerate-mode-enabled false
                  :payload-signing-enabled true
                  :dualstack-enabled true
                  :force-global-bucket-access-enabled true}})

;; (defcredential (:access-key  (env :aws-access-key))
;;   (:secret-key (env :aws-secret-key))
;;   (:endpoint (env :aws-endpoint)))

(s3/list-buckets  {:access-key (env :aws-access-key)
                   :secret-key (env :aws-secret-key)
                   :endpoint   (env :aws-endpoint)}
 {:client-config {
                  :path-style-access-enabled false
                  :chunked-encoding-disabled false
                  :accelerate-mode-enabled false
                  :payload-signing-enabled true
                  :dualstack-enabled true
                  :force-global-bucket-access-enabled true}})


(def gdelt-cred {:access-key (env :aws-access-key)
                 :secret-key (env :aws-secret-key)
                 :endpoint "us-west-2"
                 :client-config {
                                 :path-style-access-enabled false
                                 :chunked-encoding-disabled false
                                 :accelerate-mode-enabled false
                                 :payload-signing-enabled true
                                 :dualstack-enabled true
                                 :force-global-bucket-access-enabled false
                                 :cli_follow_urlparam false}
                 })

(s3/list-objects-v2 gdelt-cred
                    {:bucket-name "gdelt-open-data"})

(s3/list-objects-v2 {:bucket-name "gdelt-open-data"
} {                     :access-key (env :aws-access-key)
                     :secret-key (env :aws-secret-key)
                     :endpoint "us-east-1"
                     :client-config {
                                     :path-style-access-enabled false
                                     :chunked-encoding-disabled false
                                     :accelerate-mode-enabled false
                                     :payload-signing-enabled true
                                     :dualstack-enabled true
                                     :force-global-bucket-access-enabled false
                                     :cli_follow_urlparam false}}
                    )

()



