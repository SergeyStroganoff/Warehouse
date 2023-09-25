package com.stroganov.warehouse.service.transaction;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import com.stroganov.warehouse.exception.TransactionServiceException;
import com.stroganov.warehouse.service.warehouse.StockService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionService {

    @Autowired
    private Logger logger;

    @Autowired
    private StockService stockService;

    @Transactional
    public String doTransaction(Set<ExelTransactionRowDTO> rowDTOSet, TransactionType transactionType) throws TransactionServiceException {
        List<ExelTransactionRowDTO> rowDTOList = getRecordsNotPresentInStock(rowDTOSet);
        if (!rowDTOList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("Items not present in Stock:");
            for (ExelTransactionRowDTO dto : rowDTOList) {
                stringBuilder.append(dto.toString()).append(";").append("\n");
            }
            throw new TransactionServiceException(stringBuilder.toString());
        }
        for (ExelTransactionRowDTO dto : rowDTOSet) {
        }
        return "not";
    }

    public List<ExelTransactionRowDTO> getRecordsNotPresentInStock(Iterable<ExelTransactionRowDTO> iterableDTO) {
        List<ExelTransactionRowDTO> resultList = new ArrayList<>();
        for (ExelTransactionRowDTO dto : iterableDTO) {
            Optional<Integer> optionalStock = stockService.findStockIdByModelArticleProducerNameStyleArticle(
                    dto.getModelArticle(), dto.getManufactureName(), dto.getStyleArticle());
            if (optionalStock.isEmpty()) {
                resultList.add(dto);
            }
        }
        return resultList;
    }
}
