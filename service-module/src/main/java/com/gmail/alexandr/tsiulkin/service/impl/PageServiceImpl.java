package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.service.PageService;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PageServiceImpl implements PageService {

    @Override
    @Transactional
    public PageDTO getPage(Long countObjects, int maximumObjectOnPage, int page) {
        PageDTO pageDTO = new PageDTO();
        double countOfPages = Math.ceil((double)countObjects / maximumObjectOnPage);
        pageDTO.setCountOfPages((long) countOfPages);
        long currentPage = (page + 1);
        pageDTO.setCurrentPage(currentPage);
        long beginPage = Math.max(1, currentPage - 2);
        pageDTO.setBeginPage(beginPage);
        double endPage = Math.min(beginPage + 3, countOfPages);
        pageDTO.setEndPage((long) endPage);
        return pageDTO;
    }


}
