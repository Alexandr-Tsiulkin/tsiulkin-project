package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.service.converter.OrderConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderConverterImpl implements OrderConverter {

    @Override
    public ShowOrderDTO convert(Order order) {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        showOrderDTO.setId(order.getId());
        showOrderDTO.setNumberOfOrder(order.getNumberOfOrder());
        return showOrderDTO;
    }
}
