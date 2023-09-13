package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.item.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findByArticleAndDescription(String article, String description);

    Optional<Model> findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(String article, String description, String width, String height, String depth);
}
