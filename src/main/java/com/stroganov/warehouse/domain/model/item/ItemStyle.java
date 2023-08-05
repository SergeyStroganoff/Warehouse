package com.stroganov.warehouse.domain.model.item;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "item_style",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueStyleArticleName", columnNames = {"style_article", "style_name"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemStyle {
    @Id
    @Column(name = "item_syle_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "style_article", nullable = false, length = 10)
    private String styleArticle;

    @Column(name = "style_name", nullable = false, length = 35)
    private String styleName;
}
