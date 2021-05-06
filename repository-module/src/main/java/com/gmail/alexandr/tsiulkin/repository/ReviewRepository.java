package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {

    Long getCountReviews();

    List<Review> findAll(int startPosition, int maximumReviewsOnPage);

}
