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
import java.util.OptionalDouble;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {



    //HAYA



    private final ReviewRepository reviewRepository;

    private final CustomerRepository customerRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;


    public Review addReview(Integer customerId, Integer userId, Review review) {
        Company company = companyRepository.findCompanyById(userId);
        Customer customer = customerRepository.findCustomersById(customerId);
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
        if(r==null)throw new ApiException("not found");

        if (!r.getCustomer().getId().equals(userId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        r.setDescription(updatedReview.getDescription());
        r.setUpdatedAt(LocalDate.now());

        return reviewRepository.save(r);
    }


    public void deleteReview(Integer reviewId, Integer customerId) {
        Review r = reviewRepository.findReviewById(reviewId);
        if(r==null)throw new ApiException("not found");

        if (!r.getCustomer().getId().equals(customerId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        reviewRepository.delete(r);
    }




    //1
    public Review getReviewById(Integer reviewId, Integer userId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if(review==null)throw new ApiException("not found");

        if (!review.getCustomer().getId().equals(userId)) {
            throw new ApiException("Review does not belong to the specified customer");
        }

        return review;
    }


    //2
    public List<Review> getAllReviewsByCompanyId(Integer userId) {
        Company company = companyRepository.findCompanyById(userId);
        if (company == null)
            throw new ApiException("company not found");

        return reviewRepository.findAllReviewsByCompanyId(userId);
    }




    // 3
    public List<Review> getAllReviewsByCustomerId(Integer userId) {
        Customer customer = customerRepository.findCustomersById(userId);
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



    //5
    public double getAverageRatingByCompanyName(String name) {
        Company company = companyRepository.findCompanyByName(name);
        if (company == null)
            throw new ApiException("Company not found");

        Set<Review> reviews = company.getReviews();
        if (reviews.isEmpty())
            return 0.0; // Return 0 if there are no reviews for the company

        OptionalDouble average = reviews.stream()
                .mapToInt(Review::getRating)
                .average();

        return average.isPresent() ? average.getAsDouble() : 0.0;
    }


    //6

    public int getNumberOfReviewsByCompanyName(String name) {
        Company company = companyRepository.findCompanyByName(name);
        if (company == null)
            throw new ApiException("Company not found");

        Set<Review> reviews = company.getReviews();
        return reviews.size();
    }


    //7
    public List<Review> bestEvaluationCompany(){
        List<Review> reviews=reviewRepository.searchTopByEvaluation();
        return reviews;
    }



}


