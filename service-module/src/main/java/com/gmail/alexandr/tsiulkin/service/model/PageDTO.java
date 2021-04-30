package com.gmail.alexandr.tsiulkin.service.model;

public class PageDTO {

    private Long countOfPages;
    private Long currentPage;
    private Long beginPage;
    private Long endPage;

    public Long getCountOfPages() {
        return countOfPages;
    }

    public void setCountOfPages(Long countOfPages) {
        this.countOfPages = countOfPages;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(Long beginPage) {
        this.beginPage = beginPage;
    }

    public Long getEndPage() {
        return endPage;
    }

    public void setEndPage(Long endPage) {
        this.endPage = endPage;
    }
}
