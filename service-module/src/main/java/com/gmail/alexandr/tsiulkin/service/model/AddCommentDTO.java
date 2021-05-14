package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.CommentValidationConstant.MAXIMUM_FULL_CONTENT_SIZE;

@Data
public class AddCommentDTO {
    @NotBlank
    @NotNull
    @Size(max = MAXIMUM_FULL_CONTENT_SIZE)
    private String fullContent;
}
