package com.stroganov.warehouse.domain.model.item;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;


@Entity
@Table(name = "item")
@DynamicUpdate()
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;

        if (id != item.id) return false;
        if (Double.compare(item.costPrice, costPrice) != 0) return false;
        if (Double.compare(item.sellPrice, sellPrice) != 0) return false;
        if (!Objects.equals(model, item.model)) return false;
        if (!Objects.equals(producer, item.producer)) return false;
        return Objects.equals(itemStyle, item.itemStyle);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        result = 31 * result + (itemStyle != null ? itemStyle.hashCode() : 0);
        temp = Double.doubleToLongBits(costPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(sellPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
