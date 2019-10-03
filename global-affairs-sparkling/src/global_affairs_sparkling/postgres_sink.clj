(ns global-affairs-sparkling.postgres-sink
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [next.jdbc.prepare :as prepare]
            [environ.core :refer [env]]
            [clojure.data.csv :as csv]
            [java-time :as j-time])
  (:import (java.sql  PreparedStatement )))

(def pg-table-name :dne_test);; "date_nation_events")

(def pg-db-spec {:dbtype      "postgresql"
                 :classname   "org.postgresql.Driver"
                 :dbname      (env :db-name)
                 :host        (env :db-host-url)
                 :user        (env :db-user)
                 :password    (env :db-password)
                 :ssl true
                 ;;using aws security and node only to connect to db
                 :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

(defn pg-datasource
  [db-spec]
  (jdbc/get-datasource db-spec))

(def date-nation-event-structure
  [:datec1c2ecid
   :dates
   :country_name1
   :country_name2
   :event_code
   :event_name
   :event_code_count])


(extend-protocol prepare/SettableParameter
  java.time.LocalDate
  (set-parameter [^java.time.LocalDate v ^PreparedStatement ps ^long i]
    (.setDate ps i  (java.sql.Timestamp/valueOf v))))

(defn db-insert
  [datasource table table-structure table-matrix]
  (with-open [con (jdbc/get-connection datasource)
              prep (jdbc/prepare
                    con ["insert into date_nation_events (datec1c2ecid,dates,country_name1,country_name2,event_code,event_name,event_code_count) values (?,?,?,?,?,?,?)"])]
              ;; matrix (into []
              ;;              (mapcat (fn [group]
              ;;                 (run! #(.addBatch (set-parameters % ps 2)) group)))]
    ;; (sql/insert-multi! datasource
    ;;                    table ;:date_nation_events
    ;;                    table-structure 
    ;;                    table-matrix
    ;;                    {:rewriteBatchedStatement true :large true} )
    ;;(prepare/set-parameter % prep 2)

    (extend-protocol prepare/SettableParameter
      java.time.LocalDate
      (set-parameter [^java.time.LocalDate v ^PreparedStatement ps ^long i]
        (.setDate ps i  (java.sql.Timestamp/valueOf v))))

    (prepare/execute-batch! prep table-matrix {:rewriteBatchedStatement true :batch-size 30000})
    ))

;; (defn dne-table-insert
;;   [ds data]
;;   (db-insert ds (env :db-table-name) date-nation-event-structure data))
(def dne-table-insert
  #(db-insert pg-db-spec pg-table-name date-nation-event-structure %))


(defn insert-row
  [db-source table {:keys [datec1c2ecID dates country_name1 country_name2 event_code event_name event_code_count] :as table-matrix}] 
  (sql/insert! db-source
               table {:datec1c2ecID datec1c2ecID
                      :dates dates
                      :country_name1 country_name1
                      :country_name2 country_name2
                      :event_code event_code
                      :event_name event_name
                      :event_code_count event_code_count}))

