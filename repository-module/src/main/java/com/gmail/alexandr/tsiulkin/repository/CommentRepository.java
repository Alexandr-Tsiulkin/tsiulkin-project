package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Comment;

import java.util.List;

public interface CommentRepository extends GenericRepository<Long, Comment>{
    List<Comment> findCommentByArticleId(Long id);
}
