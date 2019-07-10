(defproject global-affairs-sparkling "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[amazonica "0.3.143"]
                 [environ "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 ;[org.clojure/clojure "1.10.0"]
                 [gorillalabs/sparkling "2.1.3"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.clojure/data.csv "0.1.4"]
                 ;[org.apache.spark/spark-streaming-kafka-0-10_2.11 "2.3.1"]
                 ;[org.apache.spark/spark-core_2.10 "2.1.0"]
                 ;[org.apache.spark/spark-sql_2.10 "2.1.0"]
                 ;[org.apache.spark/spark-streaming_2.11 "2.3.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]

  :repl-options {:init-ns global-affairs-sparkling.core}
  :mai global-affairs-sparkling.core
  :profiles {:uberjar {:aot :all}
             :provided {:dependencies [;[org.apache.spark/spark-core_2.11 "2.3.1"];_2.10 "2.1.0"];
                                       [org.apache.spark/spark-core_2.10 "2.1.0"]
                                       [org.apache.spark/spark-sql_2.10 "2.1.0"]
                                       [org.apache.httpcomponents/httpclient "4.5.2"]
                                       [org.apache.hadoop/hadoop-common "3.1.0"]
                                       [org.apache.hadoop/hadoop-aws "3.1.0"]
                                       ;[com.amazonaws/aws-java-sdk "1.11.400"]
                                        ;[org.apache.spark/spark-sql_2.11 "2.3.1"];;; https://mvnrepository.com/artifact/org.apache.spark/spark-sql

                                       ;[org.apache.spark/spark-streaming-kafka-0-10_2.11 "2.3.1"]
                                       ;[org.apache.spark/spark-streaming_2.11 "2.3.1"]
                                       ]}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :profiles/dev  {}
             :profiles/test {}
             ;; :project/dev {:plugins [[lein-cljfmt "0.6.0"];[lein-dotenv "RELEASE"]
             ;;                         [lein-environ "1.1.0"]
             ;;                         [lein-auto "0.1.3"]]}
             }
 :aot [#".*" sparkling.serialization sparkling.destructuring]
 ; :java-source-paths ["src/java"]
                                        ; :jar-name "global-affairs-sparkling.jar"
                                        ; :uberjar-name "global-affairs-sparkling.jar"

  )