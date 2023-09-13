package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
