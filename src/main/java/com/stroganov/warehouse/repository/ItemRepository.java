package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByModel_ArticleAndProducer_NameAndItemStyle_StyleArticle(String article, String producerName, String styleArticle);

    @Query("""
            SELECT i.id FROM Item i
            INNER JOIN i.model m
            INNER JOIN i.producer p
            INNER JOIN i.itemStyle s
            WHERE m.article = :modelArticle
            AND p.name = :producerName AND s.styleArticle = :styleArticle""")
    public Integer findItemIdsByModelNameAndStyleArticle(
            @Param("modelArticle") String modelArticle,
            @Param("producerName") String producerName,
            @Param("styleArticle") String styleArticle);
}
