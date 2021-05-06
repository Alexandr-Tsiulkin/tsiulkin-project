package com.gmail.alexandr.tsiulkin.controller;

import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Log4j2
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = "/customer/articles")
    public String getArticlesByPagination(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = articleService.getArticlesByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "articles";
    }

    @GetMapping(value = "/customer/articles/{id}")
    public String getArticleById(Model model, @PathVariable("id") Long id) {
        ShowArticleDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article";
    }

}