package com.stroganov.warehouse.utils;

import java.util.List;

public interface DataParser<T> {
     T parse(List<String> objectList);
}
