package com.travel.domain.notification.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NotificationEvent {
    private String type; // 예: COMMENT, POST, LIKE
    private Long senderId;
    private Long receiverId;
    private Long targetId;
    private String message;
}