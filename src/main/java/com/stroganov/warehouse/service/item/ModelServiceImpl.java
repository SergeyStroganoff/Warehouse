package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Model;
import com.stroganov.warehouse.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {
    @Autowired
    ModelRepository modelRepository;

    @Override
    public Optional<Model> findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(String article, String description, String width, String height, String depth) {
        return modelRepository.findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(article, description, width, height, depth);
    }
}
