(ns kafka-exasperate.core
  (:require [clojure.tools.logging :as log]
            [environ.core :refer [env]]
 [amazonica.aws.s3 :as s3]
 [amazonica.aws.s3transfer :as s3transfer]
 [amazonica.aws.ec2 :as ec2])
  (:import [org.apache.kafka.clients.admin AdminClient AdminClientConfig NewTopic]
           org.apache.kafka.clients.consumer.KafkaConsumer
           [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
           [org.apache.kafka.common.serialization StringDeserializer StringSerializer]))

