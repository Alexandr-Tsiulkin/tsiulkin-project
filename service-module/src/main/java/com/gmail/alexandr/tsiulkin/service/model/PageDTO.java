package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDTO {

    private Long countOfPages;
    private Long currentPage;
    private Long beginPage;
    private Long endPage;
}
