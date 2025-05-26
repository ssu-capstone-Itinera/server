package com.travel.domain.placetype.dao.restaurant;

import com.travel.domain.placetype.entity.restaurant.RestaurantType;

import java.util.List;

public interface RestaurantRepositoryCustom {
    List<String> findPlaceGoogleIdsByAddressAndRestaurantType(String address, RestaurantType restaurantType);

}
