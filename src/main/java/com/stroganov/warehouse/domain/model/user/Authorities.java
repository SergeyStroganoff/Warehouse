package com.stroganov.warehouse.domain.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
/*
This class is from previous lesson;
 */
public class Authorities {
    @Id
    private String userName;
    private String authority;
}
