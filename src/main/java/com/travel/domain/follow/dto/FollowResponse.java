package com.travel.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FollowResponse {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private LocalDateTime followDate;
}
