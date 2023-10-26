package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.model.item.AlternativeArticles;
import com.stroganov.warehouse.service.item.AlternativeArticlesService;
import com.stroganov.warehouse.service.item.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransactionNordicTypeParser implements DataParser<ExelTransactionRowDTO> {
    @Autowired
    ModelService modelService;
    @Autowired
    AlternativeArticlesService alternativeArticlesService;

    @Override
    public ExelTransactionRowDTO parse(List<String> objectList) {
        ExelTransactionRowDTO exelTransactionRowDTO = new ExelTransactionRowDTO();
        String firstRow = objectList.get(0);
        String[] prefixAndArticle = firstRow.split(" ",2);
        String prefix = prefixAndArticle[0];
        String article = prefixAndArticle[1];
        int quantity = Integer.parseInt(objectList.get(1));
        ArticlePrefixType articlePrefixType = ArticlePrefixType.valueOf(prefix);
        String producerName = articlePrefixType.getProducerName();
        String styleName = articlePrefixType.getStyle();
        Optional<AlternativeArticles> alternativeArticles = alternativeArticlesService.fiendByID(article);
        if (alternativeArticles.isPresent()) {
            article = alternativeArticles.get().getModel().getArticle();
        }
        exelTransactionRowDTO.setModelArticle(article);
        exelTransactionRowDTO.setStyleArticle(styleName);
        exelTransactionRowDTO.setManufactureName(producerName);
        exelTransactionRowDTO.setCount(quantity);
        return exelTransactionRowDTO;
    }
}
