package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.service.converter.ItemConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemConverter itemConverter;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void shouldGetItemsByNumberPage() {
        int startPosition = 0;
        int maximumItemsOnPage = 10;
        List<Item> items = new ArrayList<>();
        when(itemRepository.findAll(startPosition, maximumItemsOnPage)).thenReturn(items);
        List<ShowItemDTO> itemDTOS = items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());
        PageDTO pageDTO = new PageDTO();
        pageDTO.getItems().addAll(itemDTOS);

        PageDTO itemsByPage = itemService.getItemsByPage(1);

        assertEquals(pageDTO.getItems(), itemsByPage.getItems());
    }

    @Test
    void shouldGetAllItems() {
        List<Item> itemsWithMock = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(itemsWithMock);
        List<ShowItemDTO> itemDTOSWithMock = itemsWithMock.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());

        List<Item> items = itemRepository.findAll();
        List<ShowItemDTO> itemDTOS = items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());

        assertEquals(itemDTOSWithMock, itemDTOS);
    }

    @Test
    void shouldFindItemByIdAndReturnNullIfItemNotFound() {
        Long id = 1L;
        ShowItemDTO itemById = itemService.getItemById(id);
        assertNull(itemById);
    }

    @Test
    void shouldFindItemByIdAndReturnNotNullIfItemWasFound() {
        Long id = 1L;
        ShowItemDTO showItemDTO = new ShowItemDTO();
        showItemDTO.setId(id);
        when(itemService.getItemById(id)).thenReturn(showItemDTO);

        assertNotNull(showItemDTO);
    }

    @Test
    void shouldGetItemById() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        when(itemRepository.findById(id)).thenReturn(item);
        ShowItemDTO showItemDTO = new ShowItemDTO();
        showItemDTO.setId(id);
        when(itemConverter.convert(item)).thenReturn(showItemDTO);

        ShowItemDTO itemById = itemService.getItemById(id);

        assertEquals(showItemDTO, itemById);
    }

    @Test
    void shouldAddItem() {
        AddItemDTO addItemDTO = new AddItemDTO();
        Item item = new Item();
        when(itemConverter.convert(addItemDTO)).thenReturn(item);
        boolean isAddItem = itemService.isPersist(addItemDTO);

        assertTrue(isAddItem);
    }

    @Test
    void shouldAddUuidToItem() {
        AddItemDTO addItemDTO = new AddItemDTO();
        Item item = new Item();
        when(itemConverter.convert(addItemDTO)).thenReturn(item);
        itemService.isPersist(addItemDTO);

        assertNotNull(item.getUuid());
    }

    @Test
    void shouldDeleteItemById() {
        Long id = 1L;
        boolean isDeleteItem = itemService.isDeleteById(id);

        assertTrue(isDeleteItem);
    }

    @Test
    void shouldFindItemByUuidAndReturnNullIfItemNotFound() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        ShowItemDTO itemById = itemService.getItemByUuid(uuid);
        assertNull(itemById);
    }

    @Test
    void shouldFindItemByUuidAndReturnNotNullIfItemWasFound() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        ShowItemDTO showItemDTO = new ShowItemDTO();
        showItemDTO.setUuid(uuid);
        when(itemService.getItemByUuid(uuid)).thenReturn(showItemDTO);

        assertNotNull(showItemDTO);
    }

    @Test
    void shouldGetItemByUuid() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        Item item = new Item();
        when(itemRepository.findByUuid(uuid)).thenReturn(item);
        ShowItemDTO showItemDTO = new ShowItemDTO();
        when(itemConverter.convert(item)).thenReturn(showItemDTO);
        ShowItemDTO itemByUuid = itemService.getItemByUuid(uuid);

        assertEquals(showItemDTO, itemByUuid);
    }

    @Test
    void shouldDeleteItemByUuidAndReturnTrueIfItemDeletedSuccessfully() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        Item item = new Item();
        when(itemRepository.findByUuid(uuid)).thenReturn(item);
        boolean isDeleteByUuid = itemService.isDeleteByUuid(uuid);

        assertTrue(isDeleteByUuid);
    }

    @Test
    void shouldDeleteItemByUuidAndReturnFalseIfItemByUuidWasNotFound() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        when(itemRepository.findByUuid(uuid)).thenReturn(null);
        boolean isDeleteByUuid = itemService.isDeleteByUuid(uuid);

        assertFalse(isDeleteByUuid);
    }

    @Test
    void shouldCopyItemByUuidAndReturnTrueIfItemCopiedSuccessfully() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        Item item = new Item();
        when(itemRepository.findByUuid(uuid)).thenReturn(item);
        boolean isDeleteByUuid = itemService.isCopyItemByUuid(uuid);

        assertTrue(isDeleteByUuid);
    }

    @Test
    void shouldCopyItemByUuidAndReturnFalseIfItemByUuidWasNotFound() {
        UUID uuid = UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c");
        when(itemRepository.findByUuid(uuid)).thenReturn(null);
        boolean isCopyItemByUuid = itemService.isCopyItemByUuid(uuid);

        assertFalse(isCopyItemByUuid);
    }
}