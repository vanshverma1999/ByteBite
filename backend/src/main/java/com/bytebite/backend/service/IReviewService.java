package com.bytebite.backend.service;

import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.exception.ReviewException;
import com.bytebite.backend.model.Review;
import com.bytebite.backend.request.AddReviewRequest;

import java.util.List;

public interface IReviewService {
    Review submitReview(AddReviewRequest review, Long userId) throws RestaurantException;
    void deleteReview(Long reviewId) throws ReviewException;
    double calculateAverageRating(Long restaurantId);
    Review updateReview(Long reviewId, AddReviewRequest updatedReview) throws ReviewException;
    Review findById(Long reviewId) throws ReviewException;
    Review getReview(Long reviewId) throws ReviewException;
}
