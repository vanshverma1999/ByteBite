package com.bytebite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private String title;
    private String imageUrl;
    private String description;
    private Long id;
}
