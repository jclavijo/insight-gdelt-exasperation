(ns global-affairs-sparkling.postgres-sink
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]
            [clojure.data.csv :as csv]
            [clojure.jdbc-c3p0 :as c3p0]
            ;(:import [com.mchange/c3p0 "0.9.5.2"]);using native
))

(def pg-db-spec {:dbtype     "postgresql"
                 :dbname     "global-affairs";(env :db-name)
                 :host       "mydb.server.com";(env :db-host-url)
                 :user       "-";(env :db-user)
                 :password   "-";(env :db-password)
                 :ssl true
                 ;;using aws security and node only to connect to db
                 :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

(defn db-insert
  [table-matrix]
  (jdbc/insert-multi! db-spec :DATE_NATION_EVENTS
                      nil ; column names not supplied
                      table-matrix
                      ;; [[1 "Apple" "red" 59 87]
                      ;;  [2 "Banana" "yellow" 29 92.2]
                      ;;  [3 "Peach" "fuzzy" 139 90.0]
                      ;;  [4 "Orange" "juicy" 89 88.6]]
                      ))

;; ;; pg-db instead of db-pg-spec
;; (jdbc/insert! db-pg-spec :table {:col1 42 :col2 "123"})               ;; Create
;; (jdbc/query   db-pg-spec ["SELECT * FROM table WHERE id = ?" 13])     ;; Read
;; (jdbc/update! db-pg-spec :table {:col1 77 :col2 "456"} ["id = ?" 13]) ;; Update
;; (jdbc/delete! db-pg-spec :table ["id = ?" 13])                        ;; Delete


;; c3p0 example
;;   (:import (com.mchange.v2.c3p0 ComboPooledDataSource)))
;; (defn pool
;;   [spec]
;;   (let [cpds (doto (ComboPooledDataSource.)
;;                (.setDriverClass (:classname spec))
;;                (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
;;                (.setUser (:user spec))
;;                (.setPassword (:password spec))
;;                ;; expire excess connections after 30 minutes of inactivity:
;;                (.setMaxIdleTimeExcessConnections (* 30 60))
;;                ;; expire connections after 3 hours of inactivity:
;;                (.setMaxIdleTime (* 3 60 60)))]
;;     {:datasource cpds}))
;; (def pooled-db (delay (pool db-spec)))

;; (defn db-connection [] @pooled-db)

