package com.stroganov.warehouse.domain.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Override
    public String toString() {
        return Role.valueOf(authority).getRoleName();
    }
}
