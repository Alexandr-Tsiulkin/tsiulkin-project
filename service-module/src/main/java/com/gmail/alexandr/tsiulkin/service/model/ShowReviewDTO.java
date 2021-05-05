package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class ShowReviewDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String review;
    private LocalDateTime localDateTime;
    private String status;
}
