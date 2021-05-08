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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequiredArgsConstructor
@Log4j2
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/customer/comments/add")
    public String addPage(Model model) {
        model.addAttribute("comment", new AddCommentDTO());
        return "add-comment";
    }

    @PostMapping(value = "/customer/comments/add")
    public String add(@Valid AddCommentDTO addCommentDTO, BindingResult error,
                      @RequestParam(value = "username") String userName,
                      @RequestParam(name = "article") Long id) throws ServiceException {
        if (error.hasErrors()) {
            log.info("errors:{}", error);
            return "add-comment";
        } else {
            commentService.persist(addCommentDTO, userName, id);
            log.info("go persist comment");
        }
        return "redirect:/customer/articles";
    }

}
