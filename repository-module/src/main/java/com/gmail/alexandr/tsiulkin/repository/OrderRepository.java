package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {

    Long getCountOrders();

    List<Order> findAll(Integer startPosition, int maximumOrdersOnPage);
}
