package com.travel.domain.place.service.viewcount;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceViewService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_VIEW_KEY = "place:viewCount";

    public void increaseViewCount(Long placeId) {
        redisTemplate.opsForZSet().incrementScore(REDIS_VIEW_KEY, placeId.toString(), 1);
    }
}
