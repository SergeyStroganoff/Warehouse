package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.warehouse.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("""
            select s from Stock s
            where s.item.model.article = ?1 and s.item.producer.name = ?2 and s.item.itemStyle.styleArticle = ?3""")
    Optional<Stock> findByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticle(String article, String name, String styleArticle);

    @Query("""
            select s from Stock s
            where s.item.model.article = ?1 and s.item.producer.name = ?2 and s.item.itemStyle.styleArticle = ?3 and s.warehouse.id = ?4""")
    Optional<Stock> findByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticleAndWarehouse_Id(String article, String name, String styleArticle, int id);

    @Query("""
            select (count(s) > 0) from Stock s
            where s.item.model.article = ?1 and s.item.producer.name = ?2 and s.item.itemStyle.styleArticle = ?3 and s.warehouse.id = ?4""")
    boolean existsByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticleAndWarehouseId(String article, String name, String styleArticle, int warehouseId);

    @Query("""
            select s.id from Stock s
            INNER JOIN s.item i
            INNER JOIN i.model m
            INNER JOIN i.producer p
            INNER JOIN i.itemStyle y
            WHERE m.article = :modelArticle
            AND p.name = :producerName AND y.styleArticle = :styleArticle""")
    Integer findStockIdByModelArticleProducerNameStyleArticle(
            @Param("modelArticle") String modelArticle,
            @Param("producerName") String producerName,
            @Param("styleArticle") String styleArticle);

    Page<Stock> findByItem_Producer_Id(int id, Pageable pageable);
}