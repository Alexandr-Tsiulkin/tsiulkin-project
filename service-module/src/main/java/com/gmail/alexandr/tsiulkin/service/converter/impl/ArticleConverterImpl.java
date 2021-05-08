package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.ArticleConverter;
import com.gmail.alexandr.tsiulkin.service.converter.CommentConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ArticleConstant.MAXIMUM_CHARS_FOR_SHORT_CONTENT_FIELD;

@Component
@Log4j2
@RequiredArgsConstructor
public class ArticleConverterImpl implements ArticleConverter {

    private final CommentConverter commentConverter;

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
        showArticleDTO.setFullContent(fullContent);
        if (fullContent.length() > MAXIMUM_CHARS_FOR_SHORT_CONTENT_FIELD) {
            String shortContent = fullContent.substring(1, MAXIMUM_CHARS_FOR_SHORT_CONTENT_FIELD);
            showArticleDTO.setShortContent(shortContent);
        } else {
            showArticleDTO.setShortContent(fullContent);
        }
        Set<Comment> comments = article.getComments();
        if (!comments.isEmpty()) {
            List<ShowCommentDTO> commentDTOs = comments.stream()
                    .map(commentConverter::convert)
                    .collect(Collectors.toList());
            showArticleDTO.getComments().addAll(commentDTOs);
        }
        return showArticleDTO;
    }

    @Override
    public Article convert(AddArticleDTO addArticleDTO) {
        Article article = new Article();
        String title = addArticleDTO.getTitle();
        article.setTitle(title);
        String content = addArticleDTO.getContent();
        article.setFullContent(content);
        LocalDateTime localDateTime = LocalDateTime.now();
        article.setLocalDateTime(localDateTime);
        return article;
    }

    static String getFormatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
