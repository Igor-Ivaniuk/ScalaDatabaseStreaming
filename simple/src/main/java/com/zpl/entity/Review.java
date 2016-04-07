package com.zpl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IgorIvaniuk on 07.04.2016.
 */
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer customerId;
    private Integer rating;
    private String reviewText;
}
