package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;

import java.util.List;

public interface ReviewService {

    void deleteById(Long id);

    List<ShowReviewDTO> findAllByShow();

    void changeStatusByIds(List<Long> checkedIds, List<Long> allIds);

    PageDTO getReviewsByPage(Integer page);
}
