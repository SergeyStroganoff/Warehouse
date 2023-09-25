package com.stroganov.warehouse.domain.dto.transaction;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ExelTransactionRowDTO {
    private String modelArticle;
    private String manufactureName;
    private String styleArticle;
    private int count;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Transaction string: ");
        stringBuilder
                .append(modelArticle)
                .append(", ")
                .append(manufactureName)
                .append(", ")
                .append(styleArticle);
        return stringBuilder.toString();
    }
}
