package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.OrderStatusRepository;
import com.gmail.alexandr.tsiulkin.repository.model.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class OrderStatusRepositoryImpl extends GenericRepositoryImpl<Long, OrderStatus> implements OrderStatusRepository {

    @Override
    public OrderStatus findByStatusName(String status) {
        String stringQuery = "SELECT os FROM OrderStatus as os WHERE os.status=:status";
        Query query = entityManager.createQuery(stringQuery);
        query.setParameter("status", status);
        return (OrderStatus) query.getSingleResult();
    }
}
