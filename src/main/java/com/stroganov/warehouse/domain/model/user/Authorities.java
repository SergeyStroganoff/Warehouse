package com.stroganov.warehouse.domain.model.user;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(region = "cache_region_user_entity",
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
)
public class Authorities implements GrantedAuthority {
    @Id
    private String authority;
    @Override
    public String toString() {
        return Role.valueOf(authority).getRoleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authorities that = (Authorities) o;
        return getAuthority() != null && Objects.equals(getAuthority(), that.getAuthority());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
