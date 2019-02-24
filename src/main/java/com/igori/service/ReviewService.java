package com.igori.service;

import com.igori.entity.Review;
import com.igori.model.ReviewDTO;
import com.igori.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  public List<ReviewDTO> loadReviews(int customerId) {
    List<Review> reviews = reviewRepository.findByCustomerId(customerId);
    List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
    for (Review review : reviews) {
      reviewDTOs.add(toDto(review));
    }
    return reviewDTOs;
  }

  private ReviewDTO toDto(Review review) {
    return ReviewDTO.builder()
        .id(review.getId())
        .customerId(review.getCustomerId())
        .rating(review.getRating())
        .reviewText(review.getReviewText())
        .build();
  }
}
