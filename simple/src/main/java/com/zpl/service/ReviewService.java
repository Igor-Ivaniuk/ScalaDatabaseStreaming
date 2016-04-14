package com.zpl.service;

import com.zpl.model.GenerateResponse;
import com.zpl.model.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Service
public interface ReviewService {

    GenerateResponse generateReviews(int customerCount, int reviewCountMin, int reviewCountMax);

    List<ReviewDTO> loadReviews(int customerId);
}
