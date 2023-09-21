package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionParserImpl implements DataParser<ExelTransactionRowDTO> {
    @Override
    public ExelTransactionRowDTO parse(List<String> cellValues) {
        ExelTransactionRowDTO baseExelTransactionRowDTO = new ExelTransactionRowDTO();
        baseExelTransactionRowDTO.setModelArticle(cellValues.get(0));
        baseExelTransactionRowDTO.setManufactureName(cellValues.get(1));
        baseExelTransactionRowDTO.setStyleArticle(cellValues.get(2));
        baseExelTransactionRowDTO.setCount(Integer.parseInt(cellValues.get(3)));
        return baseExelTransactionRowDTO;
    }
}
