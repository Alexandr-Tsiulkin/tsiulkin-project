package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.repository.model.ItemDetails;
import com.gmail.alexandr.tsiulkin.service.ItemService;
import com.gmail.alexandr.tsiulkin.service.converter.ItemConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.ItemConstant.MAXIMUM_ITEMS_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getPageDTO;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Override
    @Transactional
    public PageDTO getItemsByPage(int page) {
        Long countItems = itemRepository.getCountItems();
        PageDTO pageDTO = getPageDTO(page, countItems, MAXIMUM_ITEMS_ON_PAGE);
        List<Item> items = itemRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_ITEMS_ON_PAGE);
        pageDTO.getItems().addAll(items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public List<ShowItemDTO> getItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id);
        return itemConverter.convert(item);
    }

    @Override
    @Transactional
    public boolean isPersist(AddItemDTO addItemDTO) {
        Item item = itemConverter.convert(addItemDTO);
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);
        itemRepository.persist(item);
        return true;
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        itemRepository.removeById(id);
        return true;
    }

    @Override
    @Transactional
    public ShowItemDTO getItemByUuid(UUID uuid) {
        Item item = itemRepository.findByUuid(uuid);
        return itemConverter.convert(item);
    }

    @Override
    @Transactional
    public boolean isDeleteByUuid(UUID uuid) {
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            itemRepository.removeById(item.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean isCopyItemByUuid(UUID uuid) {
        Item item = itemRepository.findByUuid(uuid);
        if (Objects.nonNull(item)) {
            Item copyItem = new Item();
            copyFieldsByItem(copyItem, item);
            itemRepository.persist(copyItem);
            return true;
        }
        return false;
    }

    private void copyFieldsByItem(Item copyItem, Item item) {
        UUID uuid = UUID.randomUUID();
        copyItem.setUuid(uuid);
        String title = item.getTitle();
        copyItem.setTitle(title);
        BigDecimal price = item.getPrice();
        copyItem.setPrice(price);
        ItemDetails itemDetails = item.getItemDetails();
        copyItem.setItemDetails(itemDetails);
    }
}
