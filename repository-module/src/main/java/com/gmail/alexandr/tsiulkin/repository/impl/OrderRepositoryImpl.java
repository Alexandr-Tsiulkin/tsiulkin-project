package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.OrderRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {
}
