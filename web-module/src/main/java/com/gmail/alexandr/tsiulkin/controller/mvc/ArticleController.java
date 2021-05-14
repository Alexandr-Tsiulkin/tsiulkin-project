package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequiredArgsConstructor
@Log4j2
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = {"/customer/articles","/seller/articles"})
    public String getArticlesByPagination(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = articleService.getArticlesByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "articles";
    }

    @GetMapping(value = {"/customer/articles/{id}","/seller/articles/{id}"})
    public String getArticleById(Model model, @PathVariable("id") Long id) {
        ShowArticleDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping(value = "/seller/articles/add")
    public String addPage(Model model) {
        model.addAttribute("article", new AddArticleDTO());
        return "add-article";
    }

    @PostMapping(value = "/seller/articles/add")
    public String add(@Valid AddArticleDTO addArticleDTO, BindingResult error,
                      @RequestParam(value = "username") String userName) throws ServiceException {
        if (error.hasErrors()) {
            log.info("errors:{}", error);
            return "add-article";
        } else {
            articleService.add(addArticleDTO, userName);
            log.info("go persist article");
        }
        return "redirect:/seller/articles";
    }

    @GetMapping(value = "/seller/articles/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) throws ServiceException {
        articleService.isDeleteById(id);
        return "redirect:/seller/articles";
    }
}
