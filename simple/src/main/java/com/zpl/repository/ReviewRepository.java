package com.zpl.repository;

import com.zpl.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    List<Review> findByCustomerId(Integer customerId);
}
