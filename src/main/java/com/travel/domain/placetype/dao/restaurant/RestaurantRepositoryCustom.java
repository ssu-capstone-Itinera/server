package com.travel.domain.placetype.dao.restaurant;

import java.util.List;

import com.travel.domain.placetype.entity.cafe.CafeDoc;
import com.travel.domain.placetype.entity.cafe.CafeTag;
import com.travel.domain.placetype.entity.restaurant.RestaurantDoc;
import com.travel.domain.placetype.entity.restaurant.RestaurantType;

public interface RestaurantRepositoryCustom {
    List<RestaurantDoc> findRestaurantsByAddressAndRestaurantType(String address, RestaurantType restaurantType);

}
