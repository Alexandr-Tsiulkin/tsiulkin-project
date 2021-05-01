package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ReviewRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.service.ReviewService;
import com.gmail.alexandr.tsiulkin.service.converter.ReviewConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ReviewPaginateConstant.MAXIMUM_REVIEWS_ON_PAGE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Override
    @Transactional
    public Long getCountReviews() {
        return reviewRepository.getCountReviews();
    }

    @Override
    @Transactional
    public List<ShowReviewDTO> getAllReviews(int page) {
        int startPosition = (page - 1) * MAXIMUM_REVIEWS_ON_PAGE;
        List<Review> reviews = reviewRepository.findAll(startPosition, MAXIMUM_REVIEWS_ON_PAGE);
        logger.info("reviews: {}", reviews);
        return reviews.stream()
                .map(reviewConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        reviewRepository.removeById(id);
    }

    @Override
    public ShowReviewDTO findById(Long id) {
        Review review = reviewRepository.findById(id);
        return reviewConverter.convert(review);
    }
}
