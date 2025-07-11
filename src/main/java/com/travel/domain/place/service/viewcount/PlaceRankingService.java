package com.travel.domain.place.service.viewcount;

import com.travel.domain.place.dao.PlaceRepository;
import com.travel.domain.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaceRankingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PlaceRepository placeRepository;

    private static final String REDIS_VIEW_KEY = "place:viewCount";

    public List<Place> getTopPlaces(int limit) {
        Set<String> placeIds = redisTemplate.opsForZSet()
                .reverseRange(REDIS_VIEW_KEY, 0, limit - 1);

        if (placeIds == null || placeIds.isEmpty()) return List.of();

        List<Long> ids = placeIds.stream()
                .map(Long::parseLong)
                .toList();

        return placeRepository.findAllById(ids);
    }
}

