package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.OrderRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Order;
import com.gmail.alexandr.tsiulkin.service.converter.OrderConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderConverter orderConverter;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldGetEmptyList() {
        List<ShowOrderDTO> orders = orderService.getOrders();
        assertTrue(orders.isEmpty());
    }

    @Test
    void shouldGetOrdersList() {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        showOrderDTO.setId(1L);
        showOrderDTO.setNumberOfItems(1L);
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        when(orderConverter.convert(order)).thenReturn(showOrderDTO);
        List<ShowOrderDTO> orders = orderService.getOrders();
        assertEquals(orders.get(0).getId(), showOrderDTO.getId());
    }

}