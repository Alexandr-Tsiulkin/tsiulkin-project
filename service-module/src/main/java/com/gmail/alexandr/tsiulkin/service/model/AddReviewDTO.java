package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.ReviewConstant.MAXIMUM_REVIEW_SIZE;

@Data
public class AddReviewDTO {

    @NotBlank
    @NotNull
    @Size(max = MAXIMUM_REVIEW_SIZE)
    private String review;
}
