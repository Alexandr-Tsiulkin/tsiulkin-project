package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Review;
import com.gmail.alexandr.tsiulkin.repository.model.Status;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;

import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getFormatDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReviewConverterImplTest {

    @InjectMocks
    private ReviewConverterImpl reviewConverter;

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightId() {
        Review review = new Review();
        Long id = 1L;
        review.setId(id);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(id, showReviewDTO.getId());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightLastName() {
        User user = new User();
        String lastName = "test last name";
        user.setLastName(lastName);
        Review review = new Review();
        review.setUser(user);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(lastName, showReviewDTO.getLastName());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightFirstName() {
        User user = new User();
        String firstName = "test first name";
        user.setFirstName(firstName);
        Review review = new Review();
        review.setUser(user);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(firstName, showReviewDTO.getFirstName());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightMiddleName() {
        User user = new User();
        String middleName = "test middle name";
        user.setMiddleName(middleName);
        Review review = new Review();
        review.setUser(user);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(middleName, showReviewDTO.getMiddleName());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightReview() {
        Review review = new Review();
        String reviewString = "test review";
        review.setReview(reviewString);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(reviewString, showReviewDTO.getReview());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightDate() {
        Review review = new Review();
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = getFormatDateTime(localDateTime);
        review.setLocalDate(localDateTime);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(dateTime, showReviewDTO.getLocalDateTime());
    }

    @Test
    void shouldConvertReviewToShowReviewDTOAndReturnRightStatus() {
        Review review = new Review();
        Status status = new Status();
        String statusName = "test status";
        status.setStatus(statusName);
        review.setStatus(status);
        ShowReviewDTO showReviewDTO = reviewConverter.convert(review);

        assertEquals(statusName, showReviewDTO.getStatus());
    }
}