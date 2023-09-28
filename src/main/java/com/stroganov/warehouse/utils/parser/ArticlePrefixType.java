package com.stroganov.warehouse.utils.parser;

import lombok.Getter;

@Getter
public enum ArticlePrefixType {
    NWS("NCK", "101 matt"),
    NES("NCK", "150 matt"),
    NCS("NCK", "143"),
    NGS("NCK", "120 matt");
    private final String producerName;
    private final String style;

    ArticlePrefixType(String producerName, String style) {
        this.producerName = producerName;
        this.style = style;
    }
}
