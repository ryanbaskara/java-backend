package com.ryanbaskara.learning.infrastructure.kafka;

import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerExample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("acks", "1");

        KafkaProducer<String, String> producer = KafkaProducer.create(vertx, config);

        KafkaProducerRecord<String, String> record =
                KafkaProducerRecord.create("users", "user1", "{ \"event\": \"USER_CREATED\" }");

        producer.send(record)
                .onSuccess(metadata -> {
                    System.out.println("Message sent to " + metadata.getTopic() +
                            " partition " + metadata.getPartition() +
                            " offset " + metadata.getOffset());
                })
                .onFailure(err -> {
                    System.err.println("Error sending message: " + err.getMessage());
                });
    }
}
