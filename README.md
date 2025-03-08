````
./bin/kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic product-created-topic
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic product-created-topic --from-beginning
````