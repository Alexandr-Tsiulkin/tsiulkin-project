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
        String hql = "SELECT COUNT(r.id) FROM Review as r";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> findAll(int startPosition, int maximumReviewsOnPage) {
        String hql = "SELECT r FROM Review as r ORDER BY r.localDate ASC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maximumReviewsOnPage);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> findAllByShow() {
        String hql = "SELECT r FROM Review as r JOIN r.status as st WHERE st.status=:status ORDER BY r.localDate ASC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("status","SHOW");
        return query.getResultList();
    }
}
