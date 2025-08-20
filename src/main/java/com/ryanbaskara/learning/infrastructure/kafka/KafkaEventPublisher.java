package com.ryanbaskara.learning.infrastructure.kafka;

import io.vertx.rxjava3.kafka.client.producer.KafkaProducer;
import io.vertx.rxjava3.kafka.client.producer.KafkaProducerRecord;
import io.vertx.rxjava3.core.Vertx;
import com.ryanbaskara.learning.domain.event.EventPublisher;

import java.util.HashMap;
import java.util.Map;

public class KafkaEventPublisher implements EventPublisher {
    private final KafkaProducer<String, String> producer;

    public KafkaEventPublisher(Vertx vertx) {
        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("acks", "1");

        this.producer = KafkaProducer.create(vertx, config);
    }

    @Override
    public void publish(String topic, String key, String message) {
        KafkaProducerRecord<String, String> record =
                KafkaProducerRecord.create(topic, key, message);

        producer.send(record)
                .subscribe(
                        metadata -> {
                            System.out.println("✅ Message sent to topic " + topic +
                                    " with offset " + metadata.getOffset());
                        },
                        error -> {
                            System.err.println("❌ Failed to send message: " + error.getMessage());
                        }
                );

    }
}

