package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.ArticleConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_CHARS_FOR_SHORT_CONTEXT_FIELD;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    @Override
    public ShowArticleDTO convert(Article article) {
        ShowArticleDTO showArticleDTO = new ShowArticleDTO();
        Long id = article.getId();
        showArticleDTO.setId(id);
        LocalDateTime localDateTime = article.getLocalDateTime();
        String formatDateTime = getFormatDateTime(localDateTime);
        showArticleDTO.setDate(formatDateTime);
        String title = article.getTitle();
        showArticleDTO.setTitle(title);
        User user = article.getUser();
        if (Objects.nonNull(user)) {
            String firstName = user.getFirstName();
            showArticleDTO.setFirstName(firstName);
            String lastName = user.getLastName();
            showArticleDTO.setLastName(lastName);
        }
        String fullContent = article.getFullContent();
        if (fullContent.length() > MAXIMUM_CHARS_FOR_SHORT_CONTEXT_FIELD) {
            String shortContent = fullContent.substring(1, MAXIMUM_CHARS_FOR_SHORT_CONTEXT_FIELD);
            showArticleDTO.setShortContext(shortContent);
        } else {
            showArticleDTO.setShortContext(fullContent);
        }
        return showArticleDTO;
    }

    private String getFormatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
