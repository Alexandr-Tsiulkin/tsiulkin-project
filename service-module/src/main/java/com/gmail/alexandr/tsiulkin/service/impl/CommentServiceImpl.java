package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ArticleRepository;
import com.gmail.alexandr.tsiulkin.repository.CommentRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.CommentService;
import com.gmail.alexandr.tsiulkin.service.converter.CommentConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public void persist(AddCommentDTO addCommentDTO, String userName, Long articleId) {
        User user = userRepository.findUserByUsername(userName);
        Article article = articleRepository.findById(articleId);
        Comment comment = commentConverter.convert(addCommentDTO, user, article);
        commentRepository.persist(comment);
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        commentRepository.removeById(id);
        return true;
    }
}
