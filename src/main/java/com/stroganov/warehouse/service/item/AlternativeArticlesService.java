package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.AlternativeArticles;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AlternativeArticlesService {
    Optional<AlternativeArticles> fiendByID(String articleString);
}
