package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.PageDTO;

public interface PageService {
    PageDTO getPage(Long countObjects, int maximumObjectOnPage, int page);
}
