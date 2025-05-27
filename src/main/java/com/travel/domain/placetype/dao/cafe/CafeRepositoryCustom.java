package com.travel.domain.placetype.dao.cafe;

import java.util.List;

import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.cafe.CafeTag;

public interface CafeRepositoryCustom {
    List<CafeDoc> findCafesByAddressAndCafeTags(String address, CafeTag cafeTag);
}
