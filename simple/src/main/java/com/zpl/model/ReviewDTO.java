package com.zpl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Data
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Integer id;
    private Integer customerId;
    private Integer rating;
    private String reviewText;

}
