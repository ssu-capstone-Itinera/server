package com.travel.domain.follow.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowResponse {
    private Long memberId;
    private String nickname;
    private String profileImage;
    private LocalDateTime followDate;
}
