package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.OrderRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public Long getCountOrders() {
        String hql = "SELECT COUNT(o.id) FROM Order as o";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findAll(Integer startPosition, int maximumOrdersOnPage) {
        String hql = "SELECT o FROM Order as o ORDER BY o.localDateTime DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maximumOrdersOnPage);
        return query.getResultList();
    }
}
