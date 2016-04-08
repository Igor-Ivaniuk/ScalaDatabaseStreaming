package com.zpl.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IgorIvaniuk on 07.04.2016.
 */
@Entity
@Data
public class Review {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer customerId;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String reviewText;
}
