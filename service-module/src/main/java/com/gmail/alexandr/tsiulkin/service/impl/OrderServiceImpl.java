package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.OrderRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.service.OrderService;
import com.gmail.alexandr.tsiulkin.service.converter.OrderConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Override
    public List<ShowOrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderConverter::convert)
                .collect(Collectors.toList());
    }
}
