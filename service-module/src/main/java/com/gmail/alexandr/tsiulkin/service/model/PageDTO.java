package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {

    private Long countOfPages;
    private Long currentPage;
    private Long beginPage;
    private Long endPage;
    private List<ShowUserDTO> users = new ArrayList<>();
    private List<ShowReviewDTO> reviews = new ArrayList<>();
    private List<ShowArticleDTO> articles = new ArrayList<>();
    private List<ShowItemDTO> items = new ArrayList<>();
    private Integer startPosition;
}
