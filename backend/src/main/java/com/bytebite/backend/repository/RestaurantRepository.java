package com.bytebite.backend.repository;

import com.bytebite.backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameLikeIgnoreCase(String name);

    List<Restaurant> findByNameContainsIgnoreCase(String name);


    List<Restaurant> findByOwner_Id(Long id);
}