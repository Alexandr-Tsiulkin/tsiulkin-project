package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ArticleRepository;
import com.gmail.alexandr.tsiulkin.repository.CommentRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.CommentService;
import com.gmail.alexandr.tsiulkin.service.converter.CommentConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.gmail.alexandr.tsiulkin.service.util.SecurityUtil.getAuthentication;

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
    public ShowCommentDTO persist(AddCommentDTO addCommentDTO, Long articleId) throws ServiceException {
        Authentication authentication = getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findUserByUsername(userName);
        if (Objects.nonNull(user)) {
            Comment comment = commentConverter.convert(addCommentDTO);
            comment.setUser(user);
            Article article = articleRepository.findById(articleId);
            if (Objects.nonNull(article)) {
                comment.setArticle(article);
                commentRepository.persist(comment);
                return commentConverter.convert(comment);
            } else {
                throw new ServiceException(String.format("Article with id: %s was not found", articleId));
            }
        } else {
            throw new ServiceException(String.format("User with username: %s was not found", userName));
        }
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        commentRepository.removeById(id);
        return true;
    }
}
