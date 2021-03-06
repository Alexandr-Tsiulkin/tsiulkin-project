package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_CHARS_FOR_FULL_CONTENT_FIELD;
import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_CHARS_FOR_TITLE_FIELD;

@Data
public class ChangeArticleDTO {

    private Long id;
    @NotNull
    @Size(max = MAXIMUM_CHARS_FOR_TITLE_FIELD, message = "Title must be between 1 and 20 characters")
    private String title;
    @NotNull
    @Size(max = MAXIMUM_CHARS_FOR_FULL_CONTENT_FIELD, message = "Full content must be between 1 and 1000 characters")
    private String content;
}
