package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.item.AlternativeArticles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativeArticlesRepository extends JpaRepository<AlternativeArticles, String> {
}
