package com.travel.infra.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.domain.notification.model.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "notification-topic", groupId = "community-service")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            NotificationEvent event = mapper.readValue(message, NotificationEvent.class);

            log.info("받은 알림 이벤트: {}", event);
            // → 여기서 DB 저장, FCM 전송 등 후처리 가능
        } catch (Exception e) {
            log.error("Kafka 메시지 처리 오류", e);
        }
    }
}
