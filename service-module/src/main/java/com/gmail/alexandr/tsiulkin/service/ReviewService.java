package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;

import java.util.List;

public interface ReviewService {
    Long getCountReviews();

    List<ShowReviewDTO> getAllReviews(int page);

    void deleteById(Long id);

    List<ShowReviewDTO> findAllByShow();

    void changeStatusByIds(List<Long> ids);
}
