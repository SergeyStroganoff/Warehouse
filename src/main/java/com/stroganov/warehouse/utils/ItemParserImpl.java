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
        Map<Integer, List<Object>> exelRowMap = exelFileReader.readExelTable(exelFilePath.toString(), sheetName);
//TODO
        return null;
    }
}
