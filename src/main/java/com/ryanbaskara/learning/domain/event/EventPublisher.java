package com.ryanbaskara.learning.domain.event;

public interface EventPublisher {
    void publish(String topic, String key, String message);
}
