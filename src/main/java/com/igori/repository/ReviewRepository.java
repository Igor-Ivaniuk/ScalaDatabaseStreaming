package com.igori.repository;

import com.igori.entity.Review;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

  List<Review> findByCustomerId(Integer customerId);
}
