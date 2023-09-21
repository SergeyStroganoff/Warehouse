package com.stroganov.warehouse.utils.parser;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.slf4j.Logger;

import java.util.List;

public interface DataParser<T> {
     T parse(List<String> objectList);

}
