package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ArticleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.converter.ArticleConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_ARTICLES_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.util.SecurityUtil.getAuthentication;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getPageDTO;

@RequiredArgsConstructor
@Log4j2
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PageDTO getArticlesByPage(Integer page) {
        Long countArticles = articleRepository.getCountArticles();
        PageDTO pageDTO = getPageDTO(page, countArticles, MAXIMUM_ARTICLES_ON_PAGE);
        List<Article> articles = articleRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_ARTICLES_ON_PAGE);
        pageDTO.getArticles().addAll(articles.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public ShowArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id);
        return articleConverter.convert(article);
    }

    @Override
    @Transactional
    public List<ShowArticleDTO> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        articleRepository.removeById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean isAdd(AddArticleDTO addArticleDTO) throws ServiceException {
        Authentication authentication = getAuthentication();
        if (Objects.nonNull(authentication)) {
            String userName = authentication.getName();
            User user = userRepository.findUserByUsername(userName);
            if (Objects.nonNull(user)) {
                Article article = articleConverter.convert(addArticleDTO);
                article.setUser(user);
                articleRepository.persist(article);
                return true;
            } else {
                throw new ServiceException("User with username: " + userName + " was not found");
            }
        }
        return false;
    }

    @Override
    @Transactional
    public ShowArticleDTO changeParameterById(ChangeArticleDTO changeArticleDTO, Long id) {
        Article article = articleRepository.findById(id);
        changeArticleFields(changeArticleDTO, article);
        articleRepository.merge(article);
        return articleConverter.convert(article);
    }

    private void changeArticleFields(ChangeArticleDTO changeArticleDTO, Article article) {
        String title = changeArticleDTO.getTitle();
        if (title != null && !title.isBlank()) {
            article.setTitle(title);
        }
        String content = changeArticleDTO.getContent();
        if (content != null && !content.isBlank()) {
            article.setFullContent(content);
        }
        if (title != null && !title.isBlank() || content != null && !content.isBlank()) {
            LocalDateTime newDateTime = LocalDateTime.now();
            article.setLocalDateTime(newDateTime);
        }
    }
}
