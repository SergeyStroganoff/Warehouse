package com.stroganov.warehouse.domain.dto.user;

import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import com.stroganov.warehouse.domain.model.user.Authorities;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 4, max = 12, message = "Name (login) must be from 4 to 12 symbols")
    private String userName;
    @Size(min = 4, max = 12, message = "Password must be from 4 to 12 symbols")
    private String password;

    @NotBlank(message = "Full name can not be blank")
    private String fullName;

    @Email(message = "please text correct email")
    private String email;
    private boolean enabled;
    private Set<Authorities> authorities = new HashSet<>();
    @ToString.Exclude
    private List<WarehouseDTO> warehouseDTOList = new ArrayList<>();

    public UserDTO(String userName, String password, String fullName, String email, boolean enabled
            , List<WarehouseDTO> warehouseList, Set<Authorities> authorities) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
        this.authorities.addAll(authorities);
        this.warehouseDTOList.addAll(warehouseList);
    }

    public UserDTO(String userName, String password, String fullName, String email, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
    }
}

