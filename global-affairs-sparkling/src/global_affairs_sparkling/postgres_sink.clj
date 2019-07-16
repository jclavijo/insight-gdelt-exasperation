(ns global-affairs-sparkling.postgres-sink
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [environ.core :refer [env]]
            [clojure.data.csv :as csv]
            [clj-time.core :as time]
            ;[clj-time.jdbc :as t-jdbc]
            [clj-time.coerce :as t-coerce]
            ))


(def pg-db-spec {:dbtype      "postgresql"
                 :classname   "org.postgresql.Driver"
                 :dbname      (env :db-name)
                 :host        (env :db-host-url)
                 :user        (env :db-user)
                 :password    (env :db-password)
                 :ssl true
                 ;;using aws security and node only to connect to db
                 :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

(def pg-datasource (jdbc/get-datasource pg-db-spec))

(def date-nation-event-structure
  [:datec1c2ecid
   :dates
   :country_name1
   :country_name2
   :event_code
   :event_name
   :event_code_count])

(defn db-insert
  [datasource table table-structure table-matrix]
  (sql/insert-multi! datasource
                     table ;:date_nation_events
                     table-structure 
                     table-matrix))

(def dne-table-insert
  #(db-insert pg-datasource (env :db-table-name) date-nation-event-structure %))

(defn insert-row
  [db-source table table-matrix id] 
  (sql/insert! db-source
               table {:datec1c2ecID id
                      :dates (t-coerce/to-sql-date "2000-12-31")
                      :country_name1 "United States"
                      :country_name2 "Canada"
                      :event_code "0213"
                      :event_name "War what is it good for?"
                      :event_code_count 10
}))

;(insert-row pg-datasource :dne_test date-nation-event-structure (rand-int 1000))
