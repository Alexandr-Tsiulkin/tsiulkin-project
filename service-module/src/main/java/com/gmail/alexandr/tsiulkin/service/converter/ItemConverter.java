package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;

public interface ItemConverter {

    ShowItemDTO convert(Item item);

    Item convert(AddItemDTO addItemDTO);
}
