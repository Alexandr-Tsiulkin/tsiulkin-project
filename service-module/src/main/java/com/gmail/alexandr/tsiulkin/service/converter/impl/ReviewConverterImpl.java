package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.repository.model.Status;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.ReviewConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    @Override
    public ShowReviewDTO convert(Review review) {
        ShowReviewDTO showReviewDTO = new ShowReviewDTO();
        Long id = review.getId();
        showReviewDTO.setId(id);
        User user = review.getUser();
        if (Objects.nonNull(user)) {
            String lastName = user.getLastName();
            showReviewDTO.setLastName(lastName);
            String firstName = user.getFirstName();
            showReviewDTO.setFirstName(firstName);
            String middleName = user.getMiddleName();
            showReviewDTO.setMiddleName(middleName);
        }
        showReviewDTO.setReview(review.getReview());
        LocalDateTime date = review.getLocalDate();
        showReviewDTO.setLocalDateTime(date);
        if (Objects.nonNull(review.getStatus())) {
            Status status = review.getStatus();
            String statusName = status.getStatus();
            showReviewDTO.setStatus(statusName);
        }
        return showReviewDTO;
    }
}
