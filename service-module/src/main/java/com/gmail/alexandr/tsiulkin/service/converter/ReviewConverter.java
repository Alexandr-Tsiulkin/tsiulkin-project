package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.service.model.AddReviewDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;

public interface ReviewConverter {

    ShowReviewDTO convert(Review review);

    Review convert(AddReviewDTO addReviewDTO);
}
