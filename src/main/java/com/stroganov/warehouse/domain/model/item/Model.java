package com.stroganov.warehouse.domain.model.item;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Immutable;

import java.util.Objects;

@Entity
@Table(name = "model")
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Model {
    @Id
    @Column(name = "model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "article", unique = true, length = 12)
    private String article;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "dimension_id", referencedColumnName = "dimension_id")
    private Dimension dimension;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model model)) return false;

        if (id != model.id) return false;
        if (!Objects.equals(article, model.article)) return false;
        if (!Objects.equals(description, model.description)) return false;
        return Objects.equals(dimension, model.dimension);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (article != null ? article.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        return result;
    }
}

