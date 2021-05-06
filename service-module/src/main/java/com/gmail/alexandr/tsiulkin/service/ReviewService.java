package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.PageDTO;

import java.util.List;

public interface ReviewService {

    void deleteById(Long id);

    void changeStatusByIds(List<Long> checkedIds, List<Long> allIds);

    PageDTO getReviewsByPage(Integer page);
}
