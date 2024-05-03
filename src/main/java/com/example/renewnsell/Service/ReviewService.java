package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.*;
import com.example.renewnsell.Repository.CompanyRepository;
import com.example.renewnsell.Repository.CustomerRepository;
import com.example.renewnsell.Repository.ReviewRepository;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {




    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;


    public Review addReview(Integer customerId, Integer userId, Review review) {
        Company company = companyRepository.findCompanyById(userId);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (company == null || customer == null)
            throw new ApiException("Company or Customer not found");

        review.setCompany(company);
        review.setCustomer(customer);
        review.setCreatedAt(LocalDate.now());
        review.setUpdatedAt(null); // No update has been made yet

        return reviewRepository.save(review);
    }


    public Review updateReview(Integer reviewId, Integer userId, Review updatedReview) {
        Review r = reviewRepository.findReviewById(reviewId);

        if (!r.getCustomer().getId().equals(userId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        r.setDescription(updatedReview.getDescription());
        r.setUpdatedAt(LocalDate.now());

        return reviewRepository.save(r);
    }


    public void deleteReview(Integer reviewId, Integer customerId) {
        Review r = reviewRepository.findReviewById(reviewId);

        if (!r.getCustomer().getId().equals(customerId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        reviewRepository.delete(r);
    }




    //1 للكاستمر
    public Review getReviewById(Integer reviewId, Integer userId) {
        Review review = reviewRepository.findReviewById(reviewId);

        if (!review.getCustomer().getId().equals(userId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        return review;
    }


    //2 للشركه تشوف تقييماتهم
    public List<Review> getAllReviewsByCompanyId(Integer userId) {
        Company company = companyRepository.findCompanyById(userId);
        if (company == null)
            throw new ApiException("company not found");

        return reviewRepository.findAllReviewsByCompanyId(userId);
    }




    // للكاستمر يشوف تقييماته3
    public List<Review> getAllReviewsByCustomerId(Integer userId) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null)
            throw new ApiException("customer not found");

        return reviewRepository.findAllReviewsByCustomerId(userId);
    }



    //4
    public Set<Review> getAllReviewsByCompanyName(String name) {
        Company company = companyRepository.findCompanyByName(name);
        if (company == null)
            throw new ApiException("company not found");

        return company.getReviews();
    }




}


