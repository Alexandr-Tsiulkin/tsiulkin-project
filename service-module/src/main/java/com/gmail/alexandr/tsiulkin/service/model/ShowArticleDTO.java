package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

@Data
public class ShowArticleDTO {

    private Long id;
    private String date;
    private String title;
    private String firstName;
    private String lastName;
    private String shortContext;
}
