package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemParserImplTest {
    private final List<String> rightObjectList = List.of("B12", "30", "34 1/2", "42", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200.12", Double.toString(1989.12));
    private final ItemParserImpl itemParser = new ItemParserImpl();

    @Test
    void parseItem() {
        //given
        Dimension dimension = new Dimension(0, "30", "34 1/2", "42");
        ItemStyle itemStyle = new ItemStyle(0, "T100", "Shaker White");
        Manufacture manufacture = new Manufacture(0, "NWS", "Nordic company");
        Model model = new Model(0, "B12", "Base cabinet", dimension);
        Item standardItem = new Item(0, model, manufacture, itemStyle, 200.12, 1989.12);
        //when
        Item testItem = itemParser.parseItem(rightObjectList);
        System.out.println(testItem);
        //then
        Assertions.assertEquals(model.getDimension(), testItem.getModel().getDimension());
        Assertions.assertEquals(model, testItem.getModel());
        Assertions.assertEquals(standardItem.getItemStyle(), testItem.getItemStyle());
        Assertions.assertEquals(standardItem.getProducer(), testItem.getProducer());
        Assertions.assertEquals(standardItem, testItem);
    }
}