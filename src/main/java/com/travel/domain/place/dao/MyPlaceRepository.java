package com.travel.domain.place.dao;

import com.travel.domain.member.entity.Member;
import com.travel.domain.place.entity.MyPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPlaceRepository extends JpaRepository<MyPlace, Long> {
    Optional<MyPlace> findByMemberAndPlaceGoogleId(Member member, String placeGoogleId);
}
