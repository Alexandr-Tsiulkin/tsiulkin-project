package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ReviewRepository;
import com.gmail.alexandr.tsiulkin.repository.StatusRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.repository.model.Status;
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
    private final StatusRepository statusRepository;

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
    @Transactional
    public List<ShowReviewDTO> findAllByShow() {
        List<Review> reviews = reviewRepository.findAllByShow();
        return reviews.stream()
                .map(reviewConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeStatusByIds(List<Long> ids) {
        List<Review> reviews = reviewRepository.findAll();
        List<Status> statuses = statusRepository.findAll();
        if (ids == null) {
            for (Review review : reviews) {
                Status statusHide = statuses.get(1);
                statusHide.getReviews().add(review);
            }
        } else {
            for (Long id : ids) {
                for (Review review : reviews) {
                        changeStatus(statuses, id, review);
                    }
                }
            }
        }


    private void changeStatus(List<Status> statuses, Long id, Review review) {
        Long reviewId = review.getId();
        if (reviewId.equals(id)) {
            Status statusShow = statuses.get(0);
            statusShow.getReviews().add(review);
        } else {
            Status statusHide = statuses.get(1);
            statusHide.getReviews().add(review);
        }
    }
}
