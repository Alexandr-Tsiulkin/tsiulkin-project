package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.CommentConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddCommentDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getFormatDateTime;

@Component
public class CommentConverterImpl implements CommentConverter {

    @Override
    public ShowCommentDTO convert(Comment comment) {
        ShowCommentDTO showCommentDTO = new ShowCommentDTO();
        Long id = comment.getId();
        showCommentDTO.setId(id);
        LocalDateTime localDateTime = comment.getLocalDateTime();
        if (Objects.nonNull(localDateTime)) {
            String formatDateTime = getFormatDateTime(localDateTime);
            showCommentDTO.setDate(formatDateTime);
        }
        String fullContent = comment.getFullContent();
        showCommentDTO.setFullContent(fullContent);
        User user = comment.getUser();
        if (Objects.nonNull(user)) {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String fullName = String.format("%s %s", firstName, lastName);
            showCommentDTO.setFullName(fullName);
        }
        return showCommentDTO;
    }

    @Override
    public Comment convert(AddCommentDTO addCommentDTO) {
        Comment comment = new Comment();
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setLocalDateTime(localDateTime);
        String fullContent = addCommentDTO.getFullContent();
        comment.setFullContent(fullContent);
        return comment;
    }
}
