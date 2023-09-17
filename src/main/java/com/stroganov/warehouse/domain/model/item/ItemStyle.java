package com.stroganov.warehouse.domain.model.item;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.util.Objects;

@Entity
@Table(name = "item_style",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueStyleArticleName", columnNames = {"style_article", "style_name"})})
@Immutable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemStyle itemStyle)) return false;

        if (id != itemStyle.id) return false;
        if (!Objects.equals(styleArticle, itemStyle.styleArticle))
            return false;
        return Objects.equals(styleName, itemStyle.styleName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (styleArticle != null ? styleArticle.hashCode() : 0);
        result = 31 * result + (styleName != null ? styleName.hashCode() : 0);
        return result;
    }
}
