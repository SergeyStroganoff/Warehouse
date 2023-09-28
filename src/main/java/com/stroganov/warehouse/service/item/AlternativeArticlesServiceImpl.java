package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.AlternativeArticles;
import com.stroganov.warehouse.repository.AlternativeArticlesRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Setter
public class AlternativeArticlesServiceImpl {
    @Autowired
    AlternativeArticlesRepository alternativeArticlesRepository;

    public Optional<AlternativeArticles> fiendByID(String articleString) {
        return alternativeArticlesRepository.findById(articleString);
    }
}
