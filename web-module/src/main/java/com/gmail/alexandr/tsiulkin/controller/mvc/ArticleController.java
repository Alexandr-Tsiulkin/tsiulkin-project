package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ARTICLES_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;

@RequiredArgsConstructor
@Log4j2
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = {CUSTOMER_PATH + ARTICLES_PATH,
            SELLER_PATH + ARTICLES_PATH})
    public String getArticlesByPagination(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = articleService.getArticlesByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "articles";
    }

    @GetMapping(value = {CUSTOMER_PATH + ARTICLES_PATH + "/{id}",
            SELLER_PATH + ARTICLES_PATH + "/{id}"})
    public String getArticleById(Model model, @PathVariable("id") Long id) {
        ShowArticleDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        if (!model.containsAttribute("changeArticle")) {
            model.addAttribute("changeArticle", new ChangeArticleDTO());
        }
        return "article";
    }

    @GetMapping(value = SELLER_PATH + ARTICLES_PATH + "/add")
    public String addPage(Model model) {
        model.addAttribute("article", new AddArticleDTO());
        return "add-article";
    }

    @PostMapping(value = SELLER_PATH + ARTICLES_PATH + "/add")
    public String add(@Valid AddArticleDTO addArticleDTO, BindingResult error) throws ServiceException {
        if (error.hasErrors()) {
            return "add-article";
        } else {
            articleService.isAdd(addArticleDTO);
        }
        return "redirect:/seller/articles";
    }

    @GetMapping(value = SELLER_PATH + ARTICLES_PATH + "/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) throws ServiceException {
        articleService.isDeleteById(id);
        return "redirect:/seller/articles";
    }

    @PostMapping(value = "/seller/articles/{id}/change-parameter")
    public String changeParameterById(@Valid @ModelAttribute("changeArticle") ChangeArticleDTO changeArticleDTO,
                                      BindingResult result,
                                      @PathVariable Long id,
                                      RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeArticle", result);
            redirectAttributes.addFlashAttribute("changeArticle", changeArticleDTO);
            return String.format("redirect:/seller/articles/%d", id);
        }
        articleService.changeParameterById(changeArticleDTO, id);
        return String.format("redirect:/seller/articles/%d", id);
    }
}
