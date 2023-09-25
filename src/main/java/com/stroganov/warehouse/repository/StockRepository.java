package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.warehouse.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("""
            select s from Stock s
            where s.item.model.article = ?1 and s.item.producer.name = ?2 and s.item.itemStyle.styleArticle = ?3""")
    Optional<Stock> findByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticle(String article, String name, String styleArticle);
}
