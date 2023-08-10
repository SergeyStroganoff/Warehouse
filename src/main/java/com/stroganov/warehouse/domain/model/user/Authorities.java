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
//@ToString

public class Authorities implements GrantedAuthority {
    @Id
   // @Column(name = "authority_id")
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  private int id;
    private String authority;
}
