package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.CommentService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
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

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.COMMENTS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;

@RequiredArgsConstructor
@Log4j2
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = CUSTOMER_PATH + COMMENTS_PATH + "/add")
    public String addPage(Model model) {
        model.addAttribute("comment", new AddCommentDTO());
        return "add-comment";
    }

    @PostMapping(value = CUSTOMER_PATH + COMMENTS_PATH + "/add")
    public String add(@Valid AddCommentDTO addCommentDTO, BindingResult error,
                      @RequestParam(name = "article") Long id) throws ServiceException {
        if (error.hasErrors()) {
            log.info("errors:{}", error);
            return "add-comment";
        } else {
            commentService.persist(addCommentDTO, id);
        }
        return "redirect:/customer/articles";
    }

    @GetMapping(value = SELLER_PATH + COMMENTS_PATH + "/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) throws ServiceException {
        commentService.isDeleteById(id);
        return "redirect:/seller/articles";
    }

}
