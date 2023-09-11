package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.exception.FileParsingException;

import java.nio.file.Path;
import java.util.Set;

public interface ItemParser {
    Set<Item> parseExelFile(Path exelFilePath) throws FileParsingException;
}
