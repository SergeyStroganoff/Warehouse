package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Model;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ModelService {
    Optional<Model> findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(String article, String description, String width, String height, String depth);
}
