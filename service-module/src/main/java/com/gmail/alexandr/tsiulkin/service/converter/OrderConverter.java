package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;

public interface OrderConverter {

    ShowOrderDTO convert(Order order);
}
