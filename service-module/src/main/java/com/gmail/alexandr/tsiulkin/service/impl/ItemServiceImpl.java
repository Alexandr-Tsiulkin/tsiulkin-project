package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ItemRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Item;
import com.gmail.alexandr.tsiulkin.service.ItemService;
import com.gmail.alexandr.tsiulkin.service.converter.ItemConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public void persist(AddItemDTO addItemDTO) {
        Item item = itemConverter.convert(addItemDTO);
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);
        itemRepository.persist(item);
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        itemRepository.removeById(id);
        return true;
    }

    @Override
    public ShowItemDTO getItemByUuid(UUID uuid) {
        Item item = itemRepository.findByUuid(uuid);
        return itemConverter.convert(item);
    }
}
