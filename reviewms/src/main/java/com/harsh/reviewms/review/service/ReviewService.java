package com.harsh.reviewms.review.service;


import com.harsh.reviewms.review.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);

    boolean addReview(Long companyId, Review review);

    Review getReview( Long reviewId);
    boolean updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
