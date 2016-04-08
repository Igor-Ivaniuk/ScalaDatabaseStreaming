package com.zpl.service;

import com.zpl.model.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Service
public interface ReviewService {

    void generateReviews(int customerId, int count);

    List<ReviewDTO> loadReviews(int customerId);
}
