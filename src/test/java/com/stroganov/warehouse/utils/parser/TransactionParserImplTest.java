package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TransactionParserImplTest {
    private final DataParser<ExelTransactionRowDTO> itemDataParser = new TransactionParserImpl();
    private final List<String> rightObjectList = List.of("B12", "NWS", "T100", "42");

    @Test
    void parseItemTest() {
        //given
        ExelTransactionRowDTO baseExelTransactionRowDTO = new ExelTransactionRowDTO("B12", "NWS", "T100", 42);
        //when
        ExelTransactionRowDTO testExelTransactionRowDTO = itemDataParser.parse(rightObjectList);
        //then
        Assertions.assertEquals(baseExelTransactionRowDTO, testExelTransactionRowDTO);
    }
}