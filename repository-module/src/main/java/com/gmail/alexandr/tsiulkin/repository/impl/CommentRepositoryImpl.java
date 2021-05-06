package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.CommentRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> findCommentByArticleId(Long id) {
        String hql = "SELECT c FROM Comment as c WHERE c.article.id=:articleId ORDER BY c.localDateTime DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("articleId", id);
        return query.getResultList();
    }
}
