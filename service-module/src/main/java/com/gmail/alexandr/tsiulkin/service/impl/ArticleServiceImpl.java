package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ArticleRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.converter.ArticleConverter;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_ARTICLES_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.impl.ReviewServiceImpl.getPageDTO;

@RequiredArgsConstructor
@Log4j2
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    @Override
    @Transactional
    public PageDTO getArticlesByPage(Integer page) {
        Long countReviews = articleRepository.getCountArticles();
        PageDTO pageDTO = getPageDTO(page, countReviews, MAXIMUM_ARTICLES_ON_PAGE);
        log.info("pageDTO: {}", pageDTO);
        List<Article> articles = articleRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_ARTICLES_ON_PAGE);
        pageDTO.getArticles().addAll(articles.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }
}
