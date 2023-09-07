package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@NoArgsConstructor
public class ItemParserImpl implements ItemParser {
    @Value("${parser.settings.sheetName}")
    String sheetName;
    @Autowired
    private ExelFileReader exelFileReader;

    @Override
    public List<Item> parseExelFile(Path exelFilePath) throws IOException, FileExtensionError, NoSuchSheetException {
        List<Item> itemList = new ArrayList<>();
        Map<Integer, List<Object>> exelRowMap = exelFileReader.readExelTable(exelFilePath.toString(), sheetName);
        verifyExelData(exelRowMap);
        for (Map.Entry<Integer, List<Object>> entry : exelRowMap.entrySet()) {
            entry.getValue();

        }
//TODO
        return null;
    }

    public void verifyExelData(Map<Integer, List<Object>> exelRowMap) {

    }
}
