package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.OrderItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;

import java.util.List;

public interface OrderService {

    List<ShowOrderDTO> getOrders();

    PageDTO getOrdersByPage(int page);

    ShowOrderDTO getOrderById(Long id) throws ServiceException;

    ShowOrderDTO changeStatusById(String status, Long id) throws ServiceException;

    ShowOrderDTO persist(ShowItemDTO showItemDTO, OrderItemDTO orderItemDTO) throws ServiceException;
}
