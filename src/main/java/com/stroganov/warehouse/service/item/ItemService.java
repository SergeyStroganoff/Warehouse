package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Item;

import java.util.Set;

public interface ItemService {
    void saveAll(Set<Item> itemList);

    Item save(Item item);

    void delete(Item item);

    void saveAllUnique(Set<Item> itemSet);
}
