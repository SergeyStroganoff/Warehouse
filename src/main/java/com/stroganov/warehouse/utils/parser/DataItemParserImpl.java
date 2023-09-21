package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.domain.model.item.*;
import com.stroganov.warehouse.utils.parser.DataParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataItemParserImpl implements DataParser<Item> {
    @Override
    public Item parse(List<String> objectList) {
        String modelArticle = objectList.get(0);
        String width = objectList.get(1);
        String height = objectList.get(2);
        String depth = objectList.get(3);
        String itemDescription = objectList.get(4);
        String styleArticle = objectList.get(5);
        String styleName = objectList.get(6);
        String manufactureName = objectList.get(7);
        String manufactureDescription = objectList.get(8);
        double costPrice = Double.parseDouble(objectList.get(9).replace("$", "").trim());
        double salePrice = Double.parseDouble(objectList.get(10).replace("$", "").trim());
        Dimension dimension = new Dimension(0, width, height, depth);
        ItemStyle itemStyle = new ItemStyle(0, styleArticle, styleName);
        Manufacture manufacture = new Manufacture(0, manufactureName, manufactureDescription);
        Model model = new Model(0, modelArticle, itemDescription, dimension);
        return new Item(0, model, manufacture, itemStyle, costPrice, salePrice);
    }
}
