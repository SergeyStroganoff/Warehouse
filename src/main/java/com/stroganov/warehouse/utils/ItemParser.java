package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.NoSuchSheetException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ItemParser {
    List<Item> parseExelFile(Path exelFilePath) throws IOException, FileExtensionError, NoSuchSheetException;
}
