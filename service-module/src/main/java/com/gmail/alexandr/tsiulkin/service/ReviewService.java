package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddReviewDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;

import java.util.List;

public interface ReviewService {

    void deleteById(Long id);

    void changeStatusByIds(List<Long> checkedIds, List<Long> allIds);

    PageDTO getReviewsByPage(Integer page);

    ShowReviewDTO add(AddReviewDTO addReviewDTO) throws ServiceException;

    PageDTO getShowReviewsByPage(int page);
}
