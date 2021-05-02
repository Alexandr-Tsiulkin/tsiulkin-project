package com.gmail.alexandr.tsiulkin.controller;

import com.gmail.alexandr.tsiulkin.service.PageService;
import com.gmail.alexandr.tsiulkin.service.ReviewService;
import com.gmail.alexandr.tsiulkin.service.model.IdsReviewDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowReviewDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.gmail.alexandr.tsiulkin.service.constant.ReviewPaginateConstant.MAXIMUM_REVIEWS_ON_PAGE;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ReviewService reviewService;
    private final PageService pageService;

    @GetMapping(value = "/admin/reviews/{page}")
    public String getUsers(Model model, @PathVariable("page") int page) {
        Long countReviews = reviewService.getCountReviews();
        logger.info("countReviews: {}", countReviews);
        PageDTO pageDTO = pageService.getPage(countReviews, MAXIMUM_REVIEWS_ON_PAGE, page);
        logger.info("count of pages: {}", pageDTO.getCountOfPages());
        logger.info("end page: {}", pageDTO.getEndPage());
        model.addAttribute("page", pageDTO);
        List<ShowReviewDTO> reviews = reviewService.getAllReviews(page);
        logger.info("reviews: {}", reviews);
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @PostMapping(value = "/admin/reviews/delete")
    public String deleteReviews(@Valid IdsReviewDTO review, BindingResult result) {
        List<Long> ids = review.getIds();
        logger.info("ids: {}", ids);
        if (!result.hasErrors()) {
            for (Long id : ids) {
                reviewService.deleteById(id);
            }
        }
        return "redirect:/admin/reviews/1";
    }

    @PostMapping(value = "/admin/reviews/show")
    public String showReviews(IdsReviewDTO review) {
        List<Long> ids = review.getIds();
        logger.info("ids: {}", ids);
        reviewService.changeStatusByIds(ids);
        return "redirect:/admin/test-review";
    }

    @GetMapping(value = "/admin/test-review")
    public String getReviews(Model model) {
        List<ShowReviewDTO> reviews = reviewService.findAllByShow();
        model.addAttribute("reviews", reviews);
        return "test-reviews";
    }
}
