package com.travel.infra.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.domain.notification.model.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendNotification(NotificationEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("notification-topic", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Kafka 메시지 직렬화 오류", e);
        }
    }
}
