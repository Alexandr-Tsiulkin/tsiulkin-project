package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;

public interface CommentService {

    ShowCommentDTO persist(AddCommentDTO addCommentDTO, Long articleId) throws ServiceException;

    boolean isDeleteById(Long id);
}
