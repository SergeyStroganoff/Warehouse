package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.ItemStyle;
import com.stroganov.warehouse.repository.ItemStyleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ItemStyleServiceImpl implements ItemStyleService {
    @Autowired
    Logger logger;

    @Autowired
    ItemStyleRepository itemStyleRepository;

    @Override
    public Optional<ItemStyle> findItemStyleByStyleArticleAndStyleName(String article, String name) {
        return itemStyleRepository.findItemStyleByStyleArticleAndStyleName(article, name);
    }
}
