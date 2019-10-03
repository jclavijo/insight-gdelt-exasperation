(ns global-affairs-sparkling.postgres-sink-test
  (:require [global-affairs-sparkling.postgres-sink :as sut]
            [clojure.test :as t]))

;; ;; pg-db instead of db-pg-spec
;; (defn insert-row
;;   [id db-source] 
;;   (sql/insert! db-source
;;                :dne_test {:datec1c2ecID id
;;                           :event_code_count 10
;;                           :country_name1 "United States"
;;                           :country_name2 "Canada"
;;                           :event_code "0213"
;;                           :event_name "War what is it good for?"
;;                           :dates (t-coerce/to-sql-date "2000-12-31")}))

;; (insert-row "201906USCA023123" ds)

;; {:dne_test/datec1c2ecid "201906USCA0213",
;;  :dne_test/event_code_count 10,
;;  :dne_test/country_name1 "United States",
;;  :dne_test/country_name2 "Canada",
;;  :dne_test/event_code "0213",
;;  :dne_test/event_name "War what is it good for?",
;;  :dne_test/dates #inst "2000-12-31T00:00:00.000-00:00"}
