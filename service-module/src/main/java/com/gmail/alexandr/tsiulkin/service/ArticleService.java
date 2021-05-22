package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;

import java.util.List;

public interface ArticleService {

    PageDTO getArticlesByPage(Integer page);

    ShowArticleDTO getArticleById(Long id) throws ServiceException;

    List<ShowArticleDTO> getArticles();

    boolean isDeleteById(Long id) throws ServiceException;

    ShowArticleDTO Add(AddArticleDTO addArticleDTO) throws ServiceException;

    ShowArticleDTO changeParameterById(ChangeArticleDTO changeArticleDTO, Long id) throws ServiceException;
}
