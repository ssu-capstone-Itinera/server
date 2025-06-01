package com.travel.domain.place.dao;

import com.travel.domain.place.entity.RecommendedPlace;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface RecommendedPlaceRepository extends JpaRepository<RecommendedPlace, Long> {


    @Query("SELECT rp FROM RecommendedPlace rp ORDER BY rp.createdDate DESC")
    List<RecommendedPlace> findTopRecent(Pageable pageable);
}

