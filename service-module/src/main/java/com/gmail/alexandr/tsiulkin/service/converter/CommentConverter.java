package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;

public interface CommentConverter {

    ShowCommentDTO convert (Comment comment);

    Comment convert (AddCommentDTO addCommentDTO, User user, Article article);
}
