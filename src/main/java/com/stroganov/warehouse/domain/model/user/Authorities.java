package com.stroganov.warehouse.domain.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Authorities implements GrantedAuthority {
    @Id
    private String authority;
}
