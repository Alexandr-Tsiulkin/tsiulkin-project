package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;

public interface ArticleConverter {

    ShowArticleDTO convert(Article article);
}
