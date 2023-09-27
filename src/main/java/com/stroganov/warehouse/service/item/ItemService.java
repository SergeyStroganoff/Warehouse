package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    void saveAll(Set<Item> itemList);

    Item save(Item item);

    void delete(Item item);

    List<Item> saveAllUnique(Set<Item> itemSet);

    Optional<Item> findItemByModelNameAndStyleArticle(String article, String producerName, String styleArticle);

    Optional<Integer> findItemIdByModelNameAndStyleArticle(String article, String producerName, String styleArticle);
}
