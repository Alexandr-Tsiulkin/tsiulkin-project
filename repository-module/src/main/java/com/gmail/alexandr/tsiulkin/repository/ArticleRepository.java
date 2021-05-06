package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    Long getCountArticles();

    List<Article> findAll(Integer startPosition, Integer maximumArticlesOnPage);

}
