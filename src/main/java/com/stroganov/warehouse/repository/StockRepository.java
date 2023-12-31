package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.warehouse.Stock;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
            select s from Stock s
            where s.item.model.article = ?1 and s.item.producer.name = ?2 and s.item.itemStyle.styleArticle = ?3 and s.warehouse.id = ?4""")
    Optional<Stock> findByItem_Model_ArticleAndItem_Producer_NameAndItem_ItemStyle_StyleArticleAndWarehouse_Id(String article, String name, String styleArticle, int id);

    @Lock(LockModeType.PESSIMISTIC_READ)
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

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from Stock s where s.warehouse.id = ?1")
    Page<Stock> findByWarehouse_Id(int id, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from Stock s where s.item.producer.id = ?1 and s.warehouse.id = ?2")
    Page<Stock> findByItem_Producer_IdAndWarehouse_Id(int producerId, int warehouseId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from Stock s where s.item.producer.id = ?1 and s.warehouse.id = ?2 and s.amount < ?3")
    Page<Stock> findByItem_Producer_IdAndWarehouse_IdAndAmountLessThan(int producerId, int warehouseId, int amount, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from Stock s where s.warehouse.id = ?1 and s.amount < ?2")
    Page<Stock> findByWarehouse_IdAndAmountLessThan(int warehouseId, int amount, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select count(s) from Stock s where s.warehouse.id = ?1")
    long getTotalAmountOfStockByWarehouseId(int warehouseId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select count(s) from Stock s where s.warehouse.id = ?1 and s.amount = ?2")
    long getStockByWarehouseAndAmount(int warehouseId, int amount);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select count(s) from Stock s where s.warehouse.id = ?1 and s.amount < ?2")
    long getStockByWarehouseAndAmountLessThan(int warehouseId, int amountLess);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select  SUM(s.amount*s.item.sellPrice) AS totalCost from Stock s where s.warehouse.id = ?1")
    Double getTotalWarehouseSellPrice(int warehouseId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
            select count(distinct s) from Stock s
            where s.item.itemStyle.styleArticle = ?1 and s.item.itemStyle.styleName = ?2""")
    long countDistinctByItem_ItemStyle_StyleArticleAndItem_ItemStyle_StyleName(String styleArticle, String styleName);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
            SELECT s.item.producer.name as manufactureName, s.item.producer.description as manufactureDescription,
            SUM(s.item.costPrice*s.amount) as totalCostGoodsInStock, SUM(s.item.sellPrice*s.amount) as totalSellGoodsInStock,
            COUNT (s.item) as totalItemsAmount
            FROM Stock s where s.warehouse.id = ?1 GROUP BY s.item.producer.name, s.item.producer.description
            """)
    List<Tuple> getStatisticsByProducer(int warehouseId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from Stock s where s.item.model.article = ?1 and s.warehouse.id = ?2")
    List<Stock> findByItem_Model_ArticleContainsAndWarehouse_Id(String article, int id);

    @Query("""
            select s from Stock s
            where s.item.model.article like concat('%', ?1) and s.item.itemStyle.styleArticle like concat('%', ?2, '%') and s.warehouse.id = ?3""")
    List<Stock> findByItem_Model_ArticleContainsAndItem_Model_ArticleContainsAndWarehouse_Id(String article, String article1, int id);
}