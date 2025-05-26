package com.travel.domain.place.dao;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.domain.place.entity.Place;
import lombok.RequiredArgsConstructor;


import java.util.List;

import static com.travel.domain.place.entity.QPlace.place;


@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Place> findByPlaceGoogleIdInOrderByRatingDescWithCursor(
            List<String> plageGoogleIds,
            Double ratingCursor,
            Long idCursor,
            int pageSize
    ) {
        BooleanExpression condition = place.placeGoogleId.in(plageGoogleIds);


        // 커서 페이징 조건
        if (ratingCursor != null && idCursor != null) {
            condition = condition.and(
                    place.rating.lt(ratingCursor)
                            .or(place.rating.eq(ratingCursor).and(place.id.lt(idCursor)))
            );
        }

        return queryFactory
                .selectFrom(place)
                .where(condition)
                .orderBy(place.rating.desc(), place.id.desc())
                .limit(pageSize)
                .fetch();
    }
}
