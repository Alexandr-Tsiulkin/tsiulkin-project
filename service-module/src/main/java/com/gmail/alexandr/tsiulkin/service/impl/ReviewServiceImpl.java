package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ReviewRepository;
import com.gmail.alexandr.tsiulkin.repository.StatusRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.repository.model.Status;
import com.gmail.alexandr.tsiulkin.service.ReviewService;
import com.gmail.alexandr.tsiulkin.service.converter.ReviewConverter;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ReviewPaginateConstant.MAXIMUM_REVIEWS_ON_PAGE;
import static java.lang.Math.*;


@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public void deleteById(Long id) {
        reviewRepository.removeById(id);
    }

    @Override
    @Transactional
    public void changeStatusByIds(List<Long> checkedIds, List<Long> allIds) {
        List<Status> statuses = statusRepository.findAll();
        if (checkedIds == null) {
            List<Review> reviews = reviewRepository.findAll();
            for (Review review : reviews) {
                Status statusHide = statuses.get(1);
                statusHide.getReviews().add(review);
            }
        } else {
            log.info("allIds: {}", allIds);
            log.info("checkedIds: {}", checkedIds);
            List<Long> uncheckedIds = removeCheckedId(checkedIds, allIds);
            log.info("uncheckedId: {}", uncheckedIds);
            for (Long id : uncheckedIds) {
                Review uncheckedReview = reviewRepository.findById(id);
                changeStatusToHide(statuses, uncheckedReview);
            }
            for (Long id : checkedIds) {
                Review checkedReview = reviewRepository.findById(id);
                changeStatusToShow(statuses, checkedReview);
            }
        }
    }

    @Override
    @Transactional
    public PageDTO getReviewsByPage(Integer page) {
        Long countReviews = reviewRepository.getCountReviews();
        PageDTO pageDTO = getPageDTO(page, countReviews, MAXIMUM_REVIEWS_ON_PAGE);
        log.info("pageDTO: {}", pageDTO);
        List<Review> reviews = reviewRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_REVIEWS_ON_PAGE);
        pageDTO.getReviews().addAll(reviews.stream()
                .map(reviewConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    static PageDTO getPageDTO(Integer page, double countReviews, int MaximumObjectsOnPage) {
        PageDTO pageDTO = new PageDTO();
        double countOfPages = ceil(countReviews / MaximumObjectsOnPage);
        pageDTO.setCountOfPages((long) countOfPages);
        long currentPage = (page + 1);
        pageDTO.setCurrentPage(currentPage);
        long beginPage = max(1, currentPage - 2);
        pageDTO.setBeginPage(beginPage);
        double endPage = min(beginPage + 2, countOfPages);
        pageDTO.setEndPage((long) endPage);
        int startPosition = (page - 1) * MaximumObjectsOnPage;
        pageDTO.setStartPosition(startPosition);
        return pageDTO;
    }

    private List<Long> removeCheckedId(List<Long> checkedIds, List<Long> allIds) {
        for (Long checkedId : checkedIds) {
            for (int i = 0; i < allIds.size(); i++) {
                if (allIds.get(i).equals(checkedId)) {
                    allIds.remove(allIds.get(i));
                }
            }
        }
        return allIds;
    }


    private void changeStatusToHide(List<Status> statuses, Review uncheckedReview) {
        if (uncheckedReview.getStatus().equals(statuses.get(0))) {
            Status statusHide = statuses.get(1);
            statusHide.getReviews().add(uncheckedReview);
        }
    }

    private void changeStatusToShow(List<Status> statuses, Review checkedReview) {
        if (checkedReview.getStatus().equals(statuses.get(1))) {
            Status statusShow = statuses.get(0);
            statusShow.getReviews().add(checkedReview);
        }
    }
}
