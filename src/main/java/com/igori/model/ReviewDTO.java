package com.igori.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

  private Integer id;
  private Integer customerId;
  private Integer rating;
  private String reviewText;

  public static ReviewDTOBuilder builder() {
    return new ReviewDTOBuilder();
  }

  public static class ReviewDTOBuilder {

    private Integer id;
    private Integer customerId;
    private Integer rating;
    private String reviewText;

    ReviewDTOBuilder() {
    }

    public ReviewDTO.ReviewDTOBuilder id(Integer id) {
      this.id = id;
      return this;
    }

    public ReviewDTO.ReviewDTOBuilder customerId(Integer customerId) {
      this.customerId = customerId;
      return this;
    }

    public ReviewDTO.ReviewDTOBuilder rating(Integer rating) {
      this.rating = rating;
      return this;
    }

    public ReviewDTO.ReviewDTOBuilder reviewText(String reviewText) {
      this.reviewText = reviewText;
      return this;
    }

    public ReviewDTO build() {
      return new ReviewDTO(id, customerId, rating, reviewText);
    }

    public String toString() {
      return "com.igori.model.ReviewDTO.ReviewDTOBuilder(id=" + this.id + ", customerId=" + this.customerId
          + ", rating=" + this.rating + ", reviewText=" + this.reviewText + ")";
    }
  }
}
