package com.stroganov.warehouse.domain.model.item;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.util.Objects;

@Entity
@Table(name = "dimension")
@Immutable
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dimension dimension)) return false;

        if (id != dimension.id) return false;
        if (!Objects.equals(width, dimension.width)) return false;
        if (!Objects.equals(height, dimension.height)) return false;
        return Objects.equals(depth, dimension.depth);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        return result;
    }
}
