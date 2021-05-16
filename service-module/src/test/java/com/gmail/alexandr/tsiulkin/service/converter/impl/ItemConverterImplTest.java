package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.ItemDetails;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemConverterImplTest {

    @InjectMocks
    private ItemConverterImpl itemConverter;

    @Test
    void shouldConvertItemToShowItemDTOAndReturnNotNullObject() {
        Item item = new Item();
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertNotNull(showItemDTO);
    }

    @Test
    void shouldConvertItemToShowItemDTOAndReturnRightId() {
        Item item = new Item();
        Long id = 1L;
        item.setId(id);
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertEquals(id, showItemDTO.getId());
    }

    @Test
    void shouldConvertItemToShowItemDTOAndReturnRightTitle() {
        Item item = new Item();
        String title = "test title";
        item.setTitle(title);
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertEquals(title, showItemDTO.getTitle());
    }

    @Test
    void shouldConvertItemToShowItemDTOAndReturnRightUuid() {
        Item item = new Item();
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        item.setUuid(uuid);
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertEquals(uuid, showItemDTO.getUuid());
    }

    @Test
    void shouldConvertItemToShowItemDTOAndReturnRightPrice() {
        Item item = new Item();
        BigDecimal price = BigDecimal.valueOf(100);
        item.setPrice(price);
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertEquals(price, showItemDTO.getPrice());
    }

    @Test
    void shouldConvertItemToShowItemDTOAndReturnRightContent() {
        ItemDetails itemDetails = new ItemDetails();
        String content = "test content";
        itemDetails.setShortContent(content);
        Item item = new Item();
        item.setItemDetails(itemDetails);
        ShowItemDTO showItemDTO = itemConverter.convert(item);

        assertEquals(content, showItemDTO.getContent());
    }

    @Test
    void shouldConvertAddItemDTOToItemAndReturnNotNullObject() {
        AddItemDTO addItemDTO = new AddItemDTO();
        Item item = itemConverter.convert(addItemDTO);

        assertNotNull(item);
    }

    @Test
    void shouldConvertAddItemDTOToItemAndReturnRightTitle() {
        AddItemDTO addItemDTO = new AddItemDTO();
        String title = "test title";
        addItemDTO.setTitle(title);
        Item item = itemConverter.convert(addItemDTO);

        assertEquals(title, item.getTitle());
    }

    @Test
    void shouldConvertAddItemDTOToItemAndReturnRightPrice() {
        AddItemDTO addItemDTO = new AddItemDTO();
        BigDecimal price = BigDecimal.valueOf(100);
        addItemDTO.setPrice(price);
        Item item = itemConverter.convert(addItemDTO);

        assertEquals(price, item.getPrice());
    }

    @Test
    void shouldConvertAddItemDTOToItemAndReturnRightContent() {
        AddItemDTO addItemDTO = new AddItemDTO();
        String content = "test content";
        addItemDTO.setContent(content);
        Item item = itemConverter.convert(addItemDTO);

        assertEquals(content, item.getItemDetails().getShortContent());
    }

}