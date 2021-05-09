package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentConverterImplTest {

    @InjectMocks
    private CommentConverterImpl commentConverter;

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnNotNullObject() {
        Comment comment = new Comment();
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertNotNull(showCommentDTO);
    }

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnRightId() {
        Comment comment = new Comment();
        Long id = 1L;
        comment.setId(id);
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertEquals(id, showCommentDTO.getId());
    }

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnRightDate() {
        Comment comment = new Comment();
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setLocalDateTime(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatLocalDate = formatter.format(localDateTime);
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertEquals(formatLocalDate, showCommentDTO.getDate());
    }

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnRightContent() {
        Comment comment = new Comment();
        String content = "test content";
        comment.setFullContent(content);
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertEquals(content, showCommentDTO.getFullContent());
    }

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnRightFirstName() {
        Comment comment = new Comment();
        String firstName = "test first name";
        User user = new User();
        user.setFirstName(firstName);
        comment.setUser(user);
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertEquals(firstName, showCommentDTO.getFirstName());
    }

    @Test
    void shouldConvertCommentToShowCommentDTOAndReturnRightLastName() {
        Comment comment = new Comment();
        String lastName = "test last name";
        User user = new User();
        user.setLastName(lastName);
        comment.setUser(user);
        ShowCommentDTO showCommentDTO = commentConverter.convert(comment);

        assertEquals(lastName, showCommentDTO.getLastName());
    }

    @Test
    void shouldConvertAddCommentDTOAndUserAndArticleToCommentAndReturnNotNullObject() {
        AddCommentDTO addCommentDTO = new AddCommentDTO();
        User user = new User();
        Article article = new Article();
        Comment comment = commentConverter.convert(addCommentDTO, user, article);

        assertNotNull(comment);
    }

    @Test
    void shouldConvertAddCommentDTOAndUserAndArticleToCommentAndReturnRightUser() {
        User user = new User();
        Comment comment = new Comment();
        comment.setUser(user);
        AddCommentDTO addCommentDTO = new AddCommentDTO();
        Article article = new Article();
        Comment convertComment = commentConverter.convert(addCommentDTO, user, article);

        assertEquals(user, convertComment.getUser());
    }

    @Test
    void shouldConvertAddCommentDTOAndUserAndArticleToCommentAndReturnRightArticle() {
        Article article = new Article();
        Comment comment = new Comment();
        comment.setArticle(article);
        AddCommentDTO addCommentDTO = new AddCommentDTO();
        User user = new User();
        Comment convertComment = commentConverter.convert(addCommentDTO, user, article);

        assertEquals(article, convertComment.getArticle());
    }

    @Test
    void shouldConvertAddCommentDTOAndUserAndArticleToCommentAndReturnRightDate() {
        Comment comment = new Comment();
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setLocalDateTime(localDateTime);
        AddCommentDTO addCommentDTO = new AddCommentDTO();
        Article article = new Article();
        User user = new User();
        Comment convertComment = commentConverter.convert(addCommentDTO, user, article);

        assertEquals(localDateTime, convertComment.getLocalDateTime());
    }

}