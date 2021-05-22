package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.service.converter.CommentConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.FormatConstant.DATE_FORMAT_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArticleConverterImplTest {

    @Mock
    private CommentConverter commentConverter;
    @InjectMocks
    private ArticleConverterImpl articleConverter;

    @Test
    void shouldConvertArticleToShowArticleDTOAndReturnRightId() {
        Article article = new Article();
        Long id = 1L;
        article.setId(id);
        ShowArticleDTO showArticleDTO = articleConverter.convert(article);

        assertEquals(id, showArticleDTO.getId());
    }

    @Test
    void shouldConvertArticleToShowArticleDTOAndReturnRightTitle() {
        Article article = new Article();
        String title = "test title";
        article.setTitle(title);
        ShowArticleDTO showArticleDTO = articleConverter.convert(article);

        assertEquals(title, showArticleDTO.getTitle());
    }

    @Test
    void shouldConvertArticleToShowArticleDTOAndReturnRightDate() {
        Article article = new Article();
        LocalDateTime localDateTime = LocalDateTime.now();
        article.setLocalDateTime(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        String formatLocalDate = formatter.format(localDateTime);
        ShowArticleDTO showArticleDTO = articleConverter.convert(article);

        assertEquals(formatLocalDate, showArticleDTO.getDate());
    }

    @Test
    void shouldConvertArticleToShowArticleDTOAndReturnRightContent() {
        Article article = new Article();
        String content = "test content";
        article.setFullContent(content);
        ShowArticleDTO showArticleDTO = articleConverter.convert(article);

        assertEquals(content, showArticleDTO.getFullContent());
        assertEquals(content, showArticleDTO.getShortContent());
    }

    @Test
    void shouldConvertArticleToShowArticleDTOAndReturnRightComments() {
        Set<Comment> comments = new HashSet<>();

        Comment comment = new Comment();
        Long commentId = 1L;
        comment.setId(commentId);
        LocalDateTime localDateTimeCommit = LocalDateTime.now();
        comment.setLocalDateTime(localDateTimeCommit);
        String contentComment = "test content comment";
        comment.setFullContent(contentComment);
        comments.add(comment);

        Comment comment2 = new Comment();
        Long commentId2 = 2L;
        comment2.setId(commentId2);
        LocalDateTime localDateTimeCommit2 = LocalDateTime.now();
        comment2.setLocalDateTime(localDateTimeCommit2);
        String contentComment2 = "test content comment2";
        comment2.setFullContent(contentComment2);
        comments.add(comment2);

        Article article = new Article();
        article.setComments(comments);
        List<ShowCommentDTO> commentDTOs = comments.stream()
                .map(commentConverter::convert)
                .collect(Collectors.toList());
        ShowArticleDTO showArticleDTO = articleConverter.convert(article);

        assertEquals(commentDTOs, showArticleDTO.getComments());
    }

    @Test
    void shouldConvertAddArticleDTOToArticleAndReturnRightTitle() {
        AddArticleDTO addArticleDTO = new AddArticleDTO();
        String title = "test";
        addArticleDTO.setTitle(title);

        Article article = articleConverter.convert(addArticleDTO);
        assertEquals(title, article.getTitle());
    }

    @Test
    void shouldConvertAddArticleDTOToArticleAndReturnRightContent() {
        AddArticleDTO addArticleDTO = new AddArticleDTO();
        String content = "test";
        addArticleDTO.setContent(content);

        Article article = articleConverter.convert(addArticleDTO);
        assertEquals(content, article.getFullContent());
    }

    @Test
    void shouldFormatLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 5, 8, 20, 2, 55);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        String formatData = "2021-05-08 20:02:55";
        assertEquals(formatData, localDateTime.format(formatter));
    }
}