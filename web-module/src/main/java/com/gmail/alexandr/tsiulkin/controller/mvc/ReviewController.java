package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.ReviewService;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REVIEWS_PATH;


@RequiredArgsConstructor
@Log4j2
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(value = ADMIN_PATH + REVIEWS_PATH)
    public String getUsers(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = reviewService.getReviewsByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "reviews";
    }

    @GetMapping(value = ADMIN_PATH + REVIEWS_PATH + "/{id}/delete")
    public String deleteReviews(@PathVariable("id") Long id) {
        reviewService.deleteById(id);
        return "redirect:/admin/reviews";
    }

    @PostMapping(value = ADMIN_PATH + REVIEWS_PATH + "/show")
    public String showReviews(@RequestParam(value = "checkedIds", required = false) List<Long> checkedIds,
                              @RequestParam("allIds") List<Long> allIds) {
        reviewService.changeStatusByIds(checkedIds, allIds);
        return "redirect:/admin/reviews";
    }
}
