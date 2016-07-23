package com.zpl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
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
            return "com.zpl.model.ReviewDTO.ReviewDTOBuilder(id=" + this.id + ", customerId=" + this.customerId + ", rating=" + this.rating + ", reviewText=" + this.reviewText + ")";
        }
    }
}
