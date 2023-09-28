package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.service.item.AlternativeArticlesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionNordicTypeParserTest {
    @Mock
    private AlternativeArticlesService alternativeArticlesService;
    @InjectMocks
    private final DataParser<ExelTransactionRowDTO> itemDataParser = new TransactionNordicTypeParser();
    private final List<String> rightObjectList = List.of("NWS B12", "42");

    @Test
    void parseItemTest() {
        //given
        ExelTransactionRowDTO baseExelTransactionRowDTO = new ExelTransactionRowDTO("B12", "NCK", "101 matt", 42);
        when(alternativeArticlesService.fiendByID(anyString())).thenReturn(Optional.empty());
        //when
        ExelTransactionRowDTO testExelTransactionRowDTO = itemDataParser.parse(rightObjectList);
        //then
        Assertions.assertEquals(baseExelTransactionRowDTO, testExelTransactionRowDTO);
    }
}