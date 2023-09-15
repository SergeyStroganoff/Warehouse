package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.*;
import com.stroganov.warehouse.exception.DataVerificationException;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
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

    @Autowired
    private Logger logger;

    @Override
    public Set<Item> parseExelFile(Path exelFilePath) throws FileParsingException {
        Set<Item> itemSet = new HashSet<>();
        Map<Integer, List<String>> exelRowMap = null;
        try {
            exelRowMap = exelFileReader.readExelTable(exelFilePath.toString(), sheetName);
            if (dataVerification.verify(exelRowMap)) {
                for (List<String> objectList : exelRowMap.values()) {
                    if ("Article".equalsIgnoreCase(objectList.get(0))) {
                        continue;
                    }
                    itemSet.add(parseItem(objectList));
                }
            }
        } catch (IOException e) {
            logger.error("Error during load saved file: " + exelFilePath, e);
            throw new FileParsingException("Failed loading file", e);
        } catch (FileExtensionError e) {
            logger.error("File format incorrect", e);
            throw new FileParsingException("File format incorrect", e);
        } catch (NoSuchSheetException e) {
            logger.error("Required sheet was not found in file: " + exelFilePath, e);
            throw new FileParsingException("Required sheet was not found in file", e);
        } catch (DataVerificationException e) {
            logger.debug("File validation error: " + exelFilePath, e);
            throw new FileParsingException("File validation error:" + e.getMessage(), e);
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
