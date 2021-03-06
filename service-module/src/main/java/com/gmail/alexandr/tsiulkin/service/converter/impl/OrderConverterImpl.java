package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.repository.model.OrderDetails;
import com.gmail.alexandr.tsiulkin.repository.model.OrderStatus;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.repository.model.UserDetails;
import com.gmail.alexandr.tsiulkin.service.converter.OrderConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Component
public class OrderConverterImpl implements OrderConverter {

    @Override
    public ShowOrderDTO convert(Order order) {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        Long id = order.getId();
        showOrderDTO.setId(id);
        UUID numberOfOrder = order.getNumberOfOrder();
        showOrderDTO.setNumberOfOrder(numberOfOrder);
        OrderStatus orderStatus = order.getOrderStatus();
        String status = orderStatus.getStatus();
        showOrderDTO.setOrderStatus(status);
        Item item = order.getItem();
        showOrderDTO.setTitle(item.getTitle());
        Long numberOfItems = order.getNumberOfItems();
        showOrderDTO.setNumberOfItems(numberOfItems);
        BigDecimal totalPrice = order.getTotalPrice();
        showOrderDTO.setTotalPrice(totalPrice);
        OrderDetails orderDetails = order.getOrderDetails();
        if (Objects.nonNull(orderDetails)) {
            User user = orderDetails.getUser();
            if (Objects.nonNull(user)) {
                String lastName = user.getLastName();
                showOrderDTO.setLastName(lastName);
                UserDetails userDetails = user.getUserDetails();
                String telephone = userDetails.getTelephone();
                showOrderDTO.setTelephone(telephone);
            }
        }
        return showOrderDTO;
    }
}
