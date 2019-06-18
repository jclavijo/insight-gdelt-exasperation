# Start Zookeeper
/usr/local/zookeeper/bin/zkServer.sh start
# Start Kafka
/usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties
# Create topic topic that the application consums from
