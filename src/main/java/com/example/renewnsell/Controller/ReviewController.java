package com.example.renewnsell.Controller;

import com.example.renewnsell.Model.Company;
import com.example.renewnsell.Model.Customer;
import com.example.renewnsell.Model.Review;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;



    @PostMapping("/add/{companyId}")
    public ResponseEntity addReview(@AuthenticationPrincipal User user,@PathVariable Integer companyId, @RequestBody @Valid Review review) {
        Review createdReview = reviewService.addReview(user.getId(),companyId,review);
        return ResponseEntity.ok(createdReview);
    }



    @GetMapping("/get/{reviewId}")
    public ResponseEntity getReviewById(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        Review review = reviewService.getReviewById(reviewId, user.getId());
        return ResponseEntity.ok(review);
    }




    @GetMapping("/get-all-c")
    public ResponseEntity getAllReviewsForCompany(@AuthenticationPrincipal User user) {
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(user.getId());
        return ResponseEntity.ok(reviews);
    }



    @GetMapping("/get-all-cu")
    public ResponseEntity getAllReviewsForCustomer(@AuthenticationPrincipal Customer customer) {
        List<Review> reviews = reviewService.getAllReviewsByCustomerId(customer.getId());
        return ResponseEntity.ok(reviews);
    }



    @PutMapping("/update/{reviewId}")
    public ResponseEntity updateReview(@AuthenticationPrincipal Integer customerId, @PathVariable Integer reviewId, @RequestBody @Valid Review updatedReview) {
        Review review = reviewService.updateReview(reviewId, customerId, updatedReview);
        return ResponseEntity.ok(review);
    }



    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal Integer customerId, @PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId, customerId);
        return ResponseEntity.ok("Review deleted successfully");



    }
}
