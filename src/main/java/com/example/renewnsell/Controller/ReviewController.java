package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.Review;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    Logger logger = LoggerFactory.getLogger(OrderCompanyController.class);


    //HAYA

    @PostMapping("/add/{companyId}")
    public ResponseEntity addReview(@AuthenticationPrincipal User user,@PathVariable Integer companyId, @RequestBody @Valid Review review) {
        reviewService.addReview(user.getId(),companyId,review);
        logger.info("addReview");
        return ResponseEntity.ok(new ApiResponse("createdReview"));
    }



    @GetMapping("/get/{reviewId}")
    public ResponseEntity getReviewById(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId, user.getId());
        logger.info("getReviewById");
        return ResponseEntity.ok(review);
    }




    @GetMapping("/get-all-c")
    public ResponseEntity getAllReviewsForCompany(@AuthenticationPrincipal User user) {
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(user.getId());
        logger.info("getAllReviewsForCompany");
        return ResponseEntity.ok(reviews);
    }



    @GetMapping("/get-all-cu")
    public ResponseEntity getAllReviewsForCustomer(@AuthenticationPrincipal User user) {
        List<Review> reviews = reviewService.getAllReviewsByCustomerId(user.getId());
        logger.info("getAllReviewsForCustomer");
        return ResponseEntity.ok(reviews);
    }



    @PutMapping("/update/{reviewId}")
    public ResponseEntity updateReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId, @RequestBody @Valid Review updatedReview) {
         reviewService.updateReview(reviewId, user.getId(), updatedReview);
         logger.info("updateReview");
        return ResponseEntity.ok(new ApiResponse("review updated"));
    }



    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId, user.getId());
        logger.info("deleteReview");
        return ResponseEntity.ok(new ApiResponse("Review deleted successfully"));

    }

    @GetMapping("/get-all-c/{name}")
    public ResponseEntity getAllReviewsByCompanyName( @PathVariable String name) {
        Set<Review> reviews = reviewService.getAllReviewsByCompanyName(name);
        logger.info("getAllReviewsByCompanyName");
        return ResponseEntity.ok(reviews);
    }


    @GetMapping("/average-rating/{name}")
    public ResponseEntity getAverageRatingByCompanyName(@PathVariable String name) {
        double averageRating = reviewService.getAverageRatingByCompanyName(name);
        logger.info("getAverageRatingByCompanyName");
        return ResponseEntity.ok(averageRating);
    }


    @GetMapping("/number-of-reviews/{name}")
    public ResponseEntity getNumberOfReviewsByCompanyName(@PathVariable String name) {
        int numberOfReviews = reviewService.getNumberOfReviewsByCompanyName(name);
        logger.info("getNumberOfReviewsByCompanyName");
        return ResponseEntity.ok(numberOfReviews);
    }

    @GetMapping("/best")
    public List<Review> bestEvaluationCompany() {
        logger.info("bestEvaluationCompany");

        return reviewService.bestEvaluationCompany();
    }

}
