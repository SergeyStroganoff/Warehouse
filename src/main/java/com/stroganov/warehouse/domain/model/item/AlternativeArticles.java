package com.stroganov.warehouse.domain.model.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Table
@Entity
@Immutable
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_ONLY
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlternativeArticles {

    @Id
    private String alternativeArticle;

    @ManyToOne
    @JoinColumn(name = "model_model_id")
    private Model model;
}
