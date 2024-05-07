package com.example.renewnsell.Repository;

import com.example.renewnsell.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(int id);

    List<Review> findAllReviewsByCompanyId(int company);

    List<Review> findAllReviewsByCustomerId(int customer);


//@Query("SELECT r from Review r ORDER BY r.rating DESC")
    List<Review> findAllByRating(Integer rating);

}
