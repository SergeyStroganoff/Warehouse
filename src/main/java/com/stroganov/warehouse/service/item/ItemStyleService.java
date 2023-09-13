package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.ItemStyle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ItemStyleService {
    Optional<ItemStyle> findItemStyleByStyleArticleAndStyleName(String article, String name);
}
