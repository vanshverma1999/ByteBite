package com.bytebite.backend.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;

    private int numRating;

    private String imageUrl;

    private LocalDateTime registrationDate;

}
