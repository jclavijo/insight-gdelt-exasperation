(defproject kafka-exasperate "0.1.0-SNAPSHOT"
  :description "Kafka-exasperate will read data from an S3 GDELT bucket using kafka producers and pass the data to a runner cluster"
  :url "http://github.com/jclavijo/insight-gdelt-exasperate"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[environ "1.1.0"]
                 [org.clojure/clojure "1.10.0"]
                 ;[org.clojure/clojure "1.8.0"]
                 [org.apache.kafka/kafka-clients "2.0.0"]
                 [org.apache.kafka/kafka_2.12 "2.0.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :repl-options {:init-ns kafka-exasperate.core}
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.6.0"]]
  :profiles {:uberjar {:aot :all}}
  )
