package com.stroganov.warehouse.domain.dto.user;

import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private boolean enabled;
    private Set<Authorities> authorities = new HashSet<>();
    private List<Warehouse> warehouseList = new ArrayList<>();

    public UserDTO(String userName, String password, String fullName, String email, boolean enabled
            , List<Warehouse> warehouseList, Set<Authorities> authorities) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
        this.authorities.addAll(authorities);
        this.warehouseList.addAll(warehouseList);
    }

    public UserDTO(String userName, String password, String fullName, String email, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
    }
}

