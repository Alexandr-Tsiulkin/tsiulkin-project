package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.OrderItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    PageDTO getItemsByPage(int page);

    List<ShowItemDTO> getItems();

    ShowItemDTO getItemById(Long id) throws ServiceException;

    ShowItemDTO persist(AddItemDTO addItemDTO);

    boolean isDeleteById(Long id);

    ShowItemDTO getItemByUuid(UUID uuid) throws ServiceException;

    boolean isDeleteByUuid(UUID uuid) throws ServiceException;

    boolean isCopyItemByUuid(UUID uuid) throws ServiceException;

    ShowOrderDTO orderItemByTitle(OrderItemDTO orderItemDTO, String id) throws ServiceException;
}
