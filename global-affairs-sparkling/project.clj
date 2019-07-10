(defproject global-affairs-sparkling "0.1.0-SNAPSHOT"
  :description "This project uses Spark through the Gorilla-labs Sparkling pluging to study the Bilinear relationship between nations through the GDELT dataset"
  :url "https://github.com/jclavijo/insight-gdelt-exasperation"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[environ "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 [gorillalabs/sparkling "2.1.4"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.clojure/data.csv "0.1.4"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                 ;[amazonica "0.3.143" :eclusions [com.fasterxml.jackson.core/jackson-annotations com.amazonaws/aws-java-sdk-signer com.amazonaws/aws-java-sdk-dlm com.fasterxml.jackson.core/jackson-core]]
                 ;[org.apache.spark/spark-core_2.10 "2.1.0"]
                 ;[org.apache.spark/spark-sql_2.10 "2.1.0"]
                 ;[org.apache.spark/spark-streaming_2.11 "2.3.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]

  :repl-options {:init-ns global-affairs-sparkling.core}
  :main global-affairs-sparkling.core
  :profiles {:uberjar {:aot :all}
             :provided {:dependencies [[org.apache.hadoop/hadoop-aws "2.7.6" :exclusions [joda-time org.slf4j/slf4j-api]]
                                       [org.apache.spark/spark-core_2.11 "2.3.1" :exclusions [commons-codec]]
                                       [com.amazonaws/aws-java-sdk "1.7.4" :exclusions [joda-time com.fasterxml.jackson.core/jackson-core]]
                                       ; https://mvnrepository.com/artifact/org.apache.spark/spark-sql
                                       [org.apache.spark/spark-sql_2.11 "2.3.1" :exclusions [commons-codec]]]}
                                        ;[org.apache.spark/spark-core_2.10 "2.1.0" :exclusions [com.fasterxml.jackson.core/jackson-annotations org.slf4j/slf4j-api org.objenesis/objenesis]]
                                        ;[org.apache.spark/spark-sql_2.10 "2.1.0" :exclusions [com.fasterxml.jackson.core/jackson-annotations org.slf4j/slf4j-api org.objenesis/objenesis]]
                                        ;[org.apache.httpcomponents/httpclient "4.5.2"]
                                        ;[org.apache.hadoop/hadoop-common "3.1.0" :exclusions [joda-time org.slf4j/slf4j-api]];"3.1.0"]2.7.3"3.1.1"
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :profiles/dev  {}
             :profiles/test {}
             :project/dev {:dependencies [[com.billpiel/sayid "0.0.17"]]
                           :plugins [[lein-cljfmt "0.6.0"]
                                     [lein-dotenv "RELEASE"]
                                     [lein-environ "1.1.0"]
                                     [lein-auto "0.1.3"]]}
             }
  :aot [#".*" sparkling.serialization sparkling.destructuring]
  ; :java-source-paths ["src/java"]
  :resource-paths ["lib/aws-java-sdk-1.7.4.jar" "lib/hadoop-aws-2.7.6.jar"]
  )
