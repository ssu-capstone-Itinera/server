package com.travel.domain.placetype.dao.cafe;

import java.util.List;

import com.travel.domain.placetype.entity.cafe.CafeTag;

public interface CafeRepositoryCustom {
    List<String> findPlaceGoogleIdsByAddressAndCafeTags(String address, CafeTag cafeTag);
}
