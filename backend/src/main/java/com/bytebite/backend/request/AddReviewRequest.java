package com.bytebite.backend.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddReviewRequest {
    private Long restaurantId;
    private Double rating;
    private String reviewText;
}
