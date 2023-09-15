package com.stroganov.warehouse.domain.dto.transaction;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExelTransactionRow {
    private String modelArticle;
    private String manufactureName;
    private String styleArticle;
    private int count;
}
