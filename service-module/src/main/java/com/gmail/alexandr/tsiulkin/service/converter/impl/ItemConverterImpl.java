package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.ItemDetails;
import com.gmail.alexandr.tsiulkin.service.converter.ItemConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Component
public class ItemConverterImpl implements ItemConverter {

    @Override
    public ShowItemDTO convert(Item item) {
        ShowItemDTO showItemDTO = new ShowItemDTO();
        Long id = item.getId();
        showItemDTO.setId(id);
        String title = item.getTitle();
        showItemDTO.setTitle(title);
        UUID uuid = item.getUuid();
        showItemDTO.setUuid(uuid);
        BigDecimal price = item.getPrice();
        showItemDTO.setPrice(price);
        ItemDetails itemDetails = item.getItemDetails();
        if (Objects.nonNull(itemDetails)) {
            String shortContent = itemDetails.getShortContent();
            showItemDTO.setContent(shortContent);
        }
        return showItemDTO;
    }

    @Override
    public Item convert(AddItemDTO addItemDTO) {
        Item item = new Item();
        String title = addItemDTO.getTitle();
        item.setTitle(title);
        BigDecimal price = addItemDTO.getPrice();
        item.setPrice(price);
        ItemDetails itemDetails = new ItemDetails();
        String content = addItemDTO.getContent();
        itemDetails.setShortContent(content);

        itemDetails.setItem(item);
        item.setItemDetails(itemDetails);
        return item;
    }
}
