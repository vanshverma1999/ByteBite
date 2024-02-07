package com.bytebite.backend.controller;

import com.bytebite.backend.exception.RestaurantException;
import com.bytebite.backend.exception.ReviewException;
import com.bytebite.backend.model.Review;
import com.bytebite.backend.model.enums.StatusCode;
import com.bytebite.backend.request.AddReviewRequest;
import com.bytebite.backend.response.Response;
import com.bytebite.backend.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<Review> submitReview(@RequestBody AddReviewRequest request, @RequestParam("userId") Long userId) throws RestaurantException {
        return ResponseEntity.status(201).body(reviewService.submitReview(request, userId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Response> deleteReview(@PathVariable("reviewId") Long reviewId) throws ReviewException {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(Response.builder().message("Review successfully deleted").statusCode(StatusCode.OK_200).build());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody AddReviewRequest request) throws ReviewException {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, request));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable("reviewId") Long reviewId) throws ReviewException {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }
}
