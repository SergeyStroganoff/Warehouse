package com.stroganov.warehouse.domain.model.item;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "dimension")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Dimension {
    @Id
    @Column(name = "dimension_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "width",length = 10)
    private String width;
    @Column(name = "height",length = 10)
    private String height;
    @Column(name = "depth",length = 10)
    private String depth;
}
