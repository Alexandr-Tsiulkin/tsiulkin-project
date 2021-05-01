package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.ReviewRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    @Override
    public Long getCountReviews() {
        String hql = "SELECT count(r.id) FROM Review as r";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> findAll(int startPosition, int maximumReviewsOnPage) {
        String hql = "select r from Review as r order by r.localDate asc";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maximumReviewsOnPage);
        return query.getResultList();
    }
}
