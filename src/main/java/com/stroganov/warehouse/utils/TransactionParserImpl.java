package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionParserImpl implements DataParser<ExelTransactionRowDTO> { //TODO
    @Override
    public ExelTransactionRowDTO parse(List<String> objectList) {
        throw new RuntimeException("not realized");
    }
}
