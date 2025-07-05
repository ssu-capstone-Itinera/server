package com.travel.domain.place.scheduler;

import com.travel.domain.place.dao.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ViewCountSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final PlaceRepository placeRepository;

    private static final String REDIS_VIEW_KEY = "place:viewCount";

    @Scheduled(cron = "0 */5 * * * *") // 매 5분마다 실행
    @Transactional
    public void syncViewCountsToDB() {
        Set<ZSetOperations.TypedTuple<String>> entries =
                redisTemplate.opsForZSet().rangeWithScores(REDIS_VIEW_KEY, 0, -1);

        if (entries == null || entries.isEmpty()) return;

        for (ZSetOperations.TypedTuple<String> entry : entries) {
            Long placeId = Long.valueOf(entry.getValue());
            int redisViewCount = entry.getScore().intValue();

            placeRepository.findById(placeId).ifPresent(place -> {
                place.setViewCount(place.getViewCount() + redisViewCount);
                placeRepository.save(place);
            });
        }

        // Redis 초기화 (선택사항)
        redisTemplate.delete(REDIS_VIEW_KEY);
    }
}

