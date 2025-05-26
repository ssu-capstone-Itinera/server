package com.travel.domain.placetype.dao.restaurant;

import java.util.List;

import com.travel.domain.placetype.entity.restaurant.RestaurantType;

public interface RestaurantRepositoryCustom {
    List<String> findPlaceGoogleIdsByAddressAndRestaurantType(
            String address, RestaurantType restaurantType);
}
