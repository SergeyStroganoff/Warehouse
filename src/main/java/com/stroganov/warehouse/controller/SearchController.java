package com.stroganov.warehouse.controller;


import com.stroganov.warehouse.domain.model.item.AlternativeArticles;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.service.item.AlternativeArticlesService;
import com.stroganov.warehouse.service.warehouse.StockService;
import com.stroganov.warehouse.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    public static final String SEARCH_PAGE = "search-page";
    @Autowired
    private AlternativeArticlesService alternativeArticlesService;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/search-article")
    public String getSearchPage(Model model) {
        String article = "";
        String styleArticle = "";
        model.addAttribute("article", article);
        model.addAttribute("styleArticle", styleArticle);
        return SEARCH_PAGE;
    }

    @PostMapping("/search-result")
    public String updateSearchPage(@ModelAttribute("article") String article, @ModelAttribute("styleArticle") String styleArticle, Model model) {
        model.addAttribute("article", article);
        model.addAttribute("styleArticle", styleArticle);
        if (article.isBlank()) {
            return SEARCH_PAGE;
        }
        Optional<Warehouse> currentWarehouse = warehouseService.getCurrentUserWarehouse();
        Warehouse warehouse = currentWarehouse.orElseThrow(RuntimeException::new);
        Optional<AlternativeArticles> alternativeArticle = alternativeArticlesService.fiendByID(article);
        if (alternativeArticle.isPresent()) {
            article = alternativeArticle.get().getModel().getArticle();
        }
        List<Stock> stockList;
        if (styleArticle.isBlank()) {
            stockList = stockService.findStockByModelArticleAndWarehouseId(article, warehouse.getId());
        } else {
            stockList = stockService.findStockByModelArticleAndStyleArticleAndWarehouseId(article, styleArticle, warehouse.getId());
        }
        model.addAttribute("stocks", stockList);
        return SEARCH_PAGE;
    }
}
