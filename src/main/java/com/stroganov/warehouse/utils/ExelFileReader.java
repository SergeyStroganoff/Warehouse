package com.stroganov.warehouse.utils;


import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.NoSuchSheetException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExelFileReader {
    Map<Integer, List<Object>> readExelTable(String file, String sheetName) throws IOException, FileExtensionError, NoSuchSheetException;
}
