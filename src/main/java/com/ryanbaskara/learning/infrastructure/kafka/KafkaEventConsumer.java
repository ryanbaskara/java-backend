package com.ryanbaskara.learning.infrastructure.kafka;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;

public class KafkaEventConsumer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id", "my-group");
        config.put("auto.offset.reset", "earliest");
        config.put("enable.auto.commit", "true");

        KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, config);

        consumer.handler(record -> {
            System.out.println("Received message: key=" + record.key() +
                    ", value=" + record.value() +
                    ", partition=" + record.partition() +
                    ", offset=" + record.offset());
        });

        consumer.subscribe("users")
                .onSuccess(v -> System.out.println("Subscribed to topic users"))
                .onFailure(err -> System.err.println("Failed to subscribe: " + err.getMessage()));
    }
}

