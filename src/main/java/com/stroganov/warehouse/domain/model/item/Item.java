package com.stroganov.warehouse.domain.model.item;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}) //CascadeType.MERGE,
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "model_id")
    private Model model;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "manufacture_id")
    private Manufacture producer;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "item_style_id", referencedColumnName = "item_syle_id")
    private ItemStyle itemStyle;

    @Column(name = "cost_price")
    private double costPrice;

    @Column(name = "sell_price")
    private double sellPrice;
}
