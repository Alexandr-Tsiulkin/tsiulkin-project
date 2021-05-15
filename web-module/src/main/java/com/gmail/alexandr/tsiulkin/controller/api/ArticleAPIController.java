package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ErrorDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ARTICLES_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;

@RestController
@RequestMapping(REST_API_USER_PATH)
@RequiredArgsConstructor
@Log4j2
public class ArticleAPIController {

    private final ArticleService articleService;

    @GetMapping(value = ARTICLES_PATH)
    public List<ShowArticleDTO> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping(value = ARTICLES_PATH + "/{id}")
    public ShowArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping(value = ARTICLES_PATH)
    public ResponseEntity<Object> addArticle(@RequestBody @Valid AddArticleDTO addArticleDTO,
                                             BindingResult result) throws ServiceException {
        if (result.hasErrors()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(result.getFieldErrors());
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        } else {
            articleService.add(addArticleDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = ARTICLES_PATH + "/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) throws ServiceException {
        boolean deleteById = articleService.isDeleteById(id);
        if (deleteById) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
