package com.stroganov.warehouse.domain.model.user;

import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.update", query = """
        update User u set u.password = ?1, u.fullName = ?2, u.email = ?3, u.enabled = ?4, u.authorities = ?5
        where u.userName = ?6""")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Cacheable
@org.hibernate.annotations.Cache(region = "cache_region_user_entity",
        usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE
)
public class User implements UserDetails {

    @Id
    @Column(name = "username", nullable = false)
    private String userName;
    private String password;
    @org.hibernate.annotations.Index(name = "fullName")
    private String fullName;
    private String email;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authorities> authorities = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(
            name = "user_warehouse",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id"))
    private List<Warehouse> warehouseList;

    @Override
    public Collection<Authorities> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userName != null && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
