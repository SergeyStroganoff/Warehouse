package com.stroganov.warehouse.service;

import com.stroganov.warehouse.exception.DataVerificationException;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import com.stroganov.warehouse.utils.parser.DataParser;
import com.stroganov.warehouse.utils.verifier.DataVerifier;
import com.stroganov.warehouse.utils.ExelFileReader;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Setter
public class DataStorageHandler<T> {
    public static final String TABLE_NAME_LINE_FLAG = "Article";
    @Value("${parser.settings.sheetName}")
    String sheetName;
    @Autowired
    private ExelFileReader exelFileReader;


    @Autowired
    private Logger logger;

    public Set<T> parseExelFile(Path exelFilePath, DataParser<T> dataParser, DataVerifier dataVerifier) throws FileParsingException {
        Map<Integer, List<String>> exelRowMap;
        Set<T> objectSet = new HashSet<>();
        try {
            exelRowMap = exelFileReader.readExelTable(exelFilePath.toString(), sheetName);
            if (dataVerifier.dataVerify(exelRowMap)) {
                for (List<String> objectList : exelRowMap.values()) {
                    if (TABLE_NAME_LINE_FLAG.equalsIgnoreCase(objectList.get(0))) {
                        continue;
                    }
                    objectSet.add(dataParser.parse(objectList));
                }
            }
            return objectSet;
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
    }
}
