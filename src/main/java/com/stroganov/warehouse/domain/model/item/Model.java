package com.stroganov.warehouse.domain.model.item;

import com.stroganov.warehouse.domain.model.item.Dimension;
import lombok.*;
import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;

@Entity
@Table(name = "model")
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

    @Column(name = "article",unique = true, length = 12)
    private String article;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "dimension_id",referencedColumnName = "dimension_id")
    private Dimension dimension;
}

