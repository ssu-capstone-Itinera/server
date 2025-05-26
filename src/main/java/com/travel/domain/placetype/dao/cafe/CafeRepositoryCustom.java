package com.travel.domain.placetype.dao.cafe;

import com.travel.domain.placetype.entity.cafe.CafeTag;

import java.util.List;

public interface CafeRepositoryCustom {
    List<String> findPlaceGoogleIdsByAddressAndCafeTags(String address, CafeTag cafeTag);
}
