package com.stroganov.warehouse.domain.model.item;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "manufacture")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Manufacture {
    @Id
    @Column(name = "manufacture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 20)
    private String name;

    @Column(name = "description")
    private String description;
}
