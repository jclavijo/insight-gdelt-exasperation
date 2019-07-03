(ns global-affairs-sparkling.postgres-sink
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]
))

;; (def db-pg-spec {:dbtype "postgresql"
;;             :dbname "mypgdatabase" ;(env :db-name )
;;             :host "mydb.server.com" ;(env :host-url)
;;             :user "myuser" ; (env :db-user)
;;             :password "secret" ; (env :db-password)
;;             :ssl true
;;             :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

;; ;; pg-db instead of db-pg-spec
;; (jdbc/insert! db-pg-spec :table {:col1 42 :col2 "123"})               ;; Create
;; (jdbc/query   db-pg-spec ["SELECT * FROM table WHERE id = ?" 13])     ;; Read
;; (jdbc/update! db-pg-spec :table {:col1 77 :col2 "456"} ["id = ?" 13]) ;; Update
;; (jdbc/delete! db-pg-spec :table ["id = ?" 13])                        ;; Delete


                                        ;(jdbc/insert!)

                                        ;[com.mchange/c3p0 "0.9.5.2"]
;; (ns example.db
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
