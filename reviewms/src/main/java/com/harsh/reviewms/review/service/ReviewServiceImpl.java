package com.harsh.reviewms.review.service;


import com.harsh.reviewms.review.Review;
import com.harsh.reviewms.review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if(companyId != null && review != null)
        {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        else
            return false;

    }

    @Override
    public Review getReview( Long reviewId) {
      return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedRevjew) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(reviewId != null)
        {
            review.setCompanyId(updatedRevjew.getCompanyId());
            review.setDescription(updatedRevjew.getDescription());
            review.setTitle(updatedRevjew.getTitle());
            review.setRating(updatedRevjew.getRating());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            reviewRepository.delete(review);
            return true;
        } else {
            return false;
        }
    }
}
