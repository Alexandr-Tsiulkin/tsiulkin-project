package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;

public interface ArticleService {

    PageDTO getArticlesByPage(Integer page);

    ShowArticleDTO getArticleById(Long id);
}
