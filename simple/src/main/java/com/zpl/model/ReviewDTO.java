package com.zpl.model;

import lombok.Data;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Data
public class ReviewDTO {

    private Integer id;
    private Integer customerId;
    private Integer rating;
    private String reviewText;

}
