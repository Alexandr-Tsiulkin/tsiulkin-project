package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;

import java.util.List;

public interface OrderService {
    List<ShowOrderDTO> getOrders();
}
