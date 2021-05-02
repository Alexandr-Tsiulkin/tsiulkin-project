package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class IdsReviewDTO {
    private List<Long> ids;
}
