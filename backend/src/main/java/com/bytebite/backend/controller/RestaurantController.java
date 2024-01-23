package com.bytebite.backend.controller;

import com.bytebite.backend.dto.RestaurantDto;
import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.model.Restaurant;
import com.bytebite.backend.model.enums.StatusCode;
import com.bytebite.backend.request.CreateRestaurantRequest;
import com.bytebite.backend.response.Response;
import com.bytebite.backend.service.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest restaurantRequest, @RequestParam("userId") Long userId) {
        return ResponseEntity.status(201).body(restaurantService.createRestaurant(restaurantRequest, userId));
    }

    @PutMapping("/")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestParam("restaurantId") Long restaurantId, @RequestBody CreateRestaurantRequest updateRequest) throws RestaurantException {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId, updateRequest));
    }

    @DeleteMapping("/")
    public ResponseEntity<Response> deleteRestaurant(@RequestParam("restaurantId") Long restaurantId) throws RestaurantException {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok(Response.builder().statusCode(StatusCode.OK_200).message("Restaurant deleted with id: " + restaurantId).build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Restaurant>> findRestaurantsByOwner(@RequestParam("userId") Long userId) throws RestaurantException {
        return ResponseEntity.ok(restaurantService.findRestaurantsByOwner(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestParam("restaurantId") Long restaurantId, @RequestParam("userId") Long userId) throws RestaurantException {
        return ResponseEntity.ok(restaurantService.addToFavorites(restaurantId, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> findRestaurantsByName(@RequestParam("restaurantName") String restaurantName){
        return ResponseEntity.ok(restaurantService.getRestaurantsByName(restaurantName));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> findAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAllRestaurant());
    }

}
