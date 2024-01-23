package com.bytebite.backend.service;

import com.bytebite.backend.dto.RestaurantDto;
import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.model.Restaurant;
import com.bytebite.backend.model.User;
import com.bytebite.backend.request.CreateRestaurantRequest;
import com.bytebite.backend.response.Response;

import java.util.List;

public interface IRestaurantService {
	Restaurant createRestaurant(CreateRestaurantRequest request, Long userId);
	Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant)
			throws RestaurantException;
	void deleteRestaurant(Long restaurantId) throws RestaurantException;
	List<Restaurant> getRestaurantsByName(String name);
	List<Restaurant>getAllRestaurant();
	Restaurant findById(Long id) throws RestaurantException;
	List<Restaurant> findRestaurantsByOwner(Long userId) throws RestaurantException;
	RestaurantDto addToFavorites(Long restaurantId, Long userId) throws RestaurantException;
}