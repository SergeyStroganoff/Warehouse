package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.*;
import com.stroganov.warehouse.exception.DataVerificationException;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@NoArgsConstructor
public class ItemParserImpl implements ItemParser {
    @Value("${parser.settings.sheetName}")
    String sheetName;
    @Autowired
    private ExelFileReader exelFileReader;

    @Autowired
    private DataVerification dataVerification;

    @Override
    public Set<Item> parseExelFile(Path exelFilePath) throws IOException, FileExtensionError, NoSuchSheetException, DataVerificationException {
        Set<Item> itemSet = new HashSet<>();
        Map<Integer, List<String>> exelRowMap = exelFileReader.readExelTable(exelFilePath.toString(), sheetName);
        if (dataVerification.verify(exelRowMap)) {
            for (List<String> objectList : exelRowMap.values()) {
                if (objectList.get(0).equals("Article")) {
                    continue;
                }
                itemSet.add(parseItem(objectList));
            }
        }
        return itemSet;
    }

    public Item parseItem(List<String> objectList) {
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
