package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.PageDTO;

public interface ArticleService {

    PageDTO getArticlesByPage(Integer page);
}
