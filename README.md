# Important Commands
````
./bin/kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic product-created-topic

./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic product-created-topic-dlt --from-beginning --property print.key=true --property print.value=true

./bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic product-created-topic --property parse.key=true --property key.separator=:
````