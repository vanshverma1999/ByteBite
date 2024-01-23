package com.bytebite.backend.service.impl;

import com.bytebite.backend.dto.RestaurantDto;
import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.model.Address;
import com.bytebite.backend.model.Restaurant;
import com.bytebite.backend.model.User;
import com.bytebite.backend.repository.AddressRepository;
import com.bytebite.backend.repository.RestaurantRepository;
import com.bytebite.backend.repository.UserRepository;
import com.bytebite.backend.request.CreateRestaurantRequest;
import com.bytebite.backend.service.IRestaurantService;
import com.bytebite.backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final IUserService userService;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, Long userId) {
        User user = userService.findById(userId);
        Address address = addressRepository.save(
                Address.builder()
                        .city(request.getAddress().getCity())
                        .country(request.getAddress().getCountry())
                        .fullName(request.getAddress().getFullName())
                        .postalCode(request.getAddress().getPostalCode())
                        .state(request.getAddress().getState())
                        .streetAddress(request.getAddress().getStreetAddress())
                        .build()
        );
        return restaurantRepository.save(
                Restaurant.builder()
                        .address(address)
                        .contactInformation(request.getContactInformation())
                        .cuisineType(request.getCuisineType())
                        .description(request.getDescription())
                        .imageUrl(request.getImageUrl())
                        .name(request.getName())
                        .openingHours(request.getOpeningHours())
                        .registrationDate(LocalDateTime.now())
                        .owner(user)
                        .build()
        );
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws RestaurantException {
        Restaurant restaurant = findById(restaurantId);
        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (updatedRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        if (updatedRestaurant.getImageUrl() != null) {
            restaurant.setImageUrl(updatedRestaurant.getImageUrl());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws RestaurantException {
        restaurantRepository.delete(findById(restaurantId));
    }

    @Override
    public List<Restaurant> getRestaurantsByName(String name) {
        return restaurantRepository.findByNameLikeIgnoreCase(name);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findById(Long id) throws RestaurantException {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantException("Restaurant not found with id: " + id));
    }

    @Override
    public List<Restaurant> findRestaurantsByOwner(Long userId) throws RestaurantException {
        return restaurantRepository.findByOwner_Id(userId);
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, Long userId) throws RestaurantException {
        Restaurant restaurant = findById(restaurantId);
        User user = userService.findById(userId);
        RestaurantDto restaurantDto =
                RestaurantDto.builder()
                        .id(restaurantId)
                        .title(restaurant.getName())
                        .description(restaurant.getDescription())
                        .imageUrl(restaurant.getImageUrl())
                        .build();
        if (user.getFavorites().contains(restaurantDto)) {
            user.getFavorites().remove(restaurantDto);
        } else {
            user.getFavorites().add(restaurantDto);
        }
        userRepository.save(user);
        return restaurantDto;
    }
}
