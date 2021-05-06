package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;

public interface CommentService {

    void persist(AddCommentDTO addCommentDTO, String userName, Long articleId);
}
