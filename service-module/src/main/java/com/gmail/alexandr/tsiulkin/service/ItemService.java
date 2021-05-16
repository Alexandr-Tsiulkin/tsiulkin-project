package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    PageDTO getItemsByPage(int page);

    List<ShowItemDTO> getItems();

    ShowItemDTO getItemById(Long id);

    boolean isPersist(AddItemDTO addItemDTO);

    boolean isDeleteById(Long id);

    ShowItemDTO getItemByUuid(UUID uuid);

    boolean isDeleteByUuid(UUID uuid);

    boolean isCopyItemByUuid(UUID uuid);
}
