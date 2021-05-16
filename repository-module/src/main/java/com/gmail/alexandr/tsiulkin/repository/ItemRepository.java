package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends GenericRepository<Long, Item> {

    Long getCountItems();

    List<Item> findAll(Integer startPosition, int maximumItemsOnPage);

    Item findByUuid(UUID uuid);
}
