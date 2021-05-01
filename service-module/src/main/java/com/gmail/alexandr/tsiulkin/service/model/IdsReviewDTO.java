package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class IdsReviewDTO {
    @NotNull
    private List<Long> ids;
}
