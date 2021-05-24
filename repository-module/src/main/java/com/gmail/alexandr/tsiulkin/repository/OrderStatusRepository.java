package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.OrderStatus;

public interface OrderStatusRepository extends GenericRepository<Long, OrderStatus> {
    OrderStatus findByStatusName(String status);
}
