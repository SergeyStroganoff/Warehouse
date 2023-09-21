package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.model.item.*;
import com.stroganov.warehouse.utils.parser.DataItemParserImpl;
import com.stroganov.warehouse.utils.parser.DataParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataItemParserImplTest {

    private final DataParser<Item> itemDataParser = new DataItemParserImpl();

    private final List<String> rightObjectList = List.of("B12", "30", "34 1/2", "42", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200.12", Double.toString(1989.12));

    @Test
    void parseItemTest() {
        //given
        Dimension dimension = new Dimension(0, "30", "34 1/2", "42");
        ItemStyle itemStyle = new ItemStyle(0, "T100", "Shaker White");
        Manufacture manufacture = new Manufacture(0, "NWS", "Nordic company");
        Model model = new Model(0, "B12", "Base cabinet", dimension);
        Item standardItem = new Item(0, model, manufacture, itemStyle, 200.12, 1989.12);
        //when
        Item testItem = itemDataParser.parse(rightObjectList);
        //then
        Assertions.assertEquals(standardItem, testItem);
    }
}