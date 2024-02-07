package com.bytebite.backend.service.impl;

import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.exception.ReviewException;
import com.bytebite.backend.model.Review;
import com.bytebite.backend.repository.ReviewRepository;
import com.bytebite.backend.request.AddReviewRequest;
import com.bytebite.backend.service.IRestaurantService;
import com.bytebite.backend.service.IReviewService;
import com.bytebite.backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final IRestaurantService restaurantService;
    private final IUserService userService;

    @Override
    public Review submitReview(AddReviewRequest review, Long userId) throws RestaurantException {
        return reviewRepository.save(
                Review.builder()
                        .rating(review.getRating())
                        .customer(userService.findById(userId))
                        .restaurant(restaurantService.findById(review.getRestaurantId()))
                        .description(review.getReviewText())
                        .build()
        );
    }

    @Override
    public void deleteReview(Long reviewId) throws ReviewException {
        reviewRepository.delete(findById(reviewId));
    }

    @Override
    public double calculateAverageRating(Long restaurantId) {
        List<Review> reviewList = reviewRepository.findByRestaurant_Id(restaurantId);
        return reviewList.stream().mapToDouble(Review::getRating).summaryStatistics().getAverage();
    }

    @Override
    public Review updateReview(Long reviewId, AddReviewRequest updatedReview) throws ReviewException {
        Review review = findById(reviewId);
        if (updatedReview.getReviewText() != null) {
            review.setDescription(updatedReview.getReviewText());
        }
        if (updatedReview.getRating() != null) {
            review.setRating(updatedReview.getRating());
        }
        return reviewRepository.save(review);
    }

    @Override
    public Review findById(Long reviewId) throws ReviewException {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewException("Review not found with Id : " + reviewId));
    }

    @Override
    public Review getReview(Long reviewId) throws ReviewException {
        return findById(reviewId);
    }
}
