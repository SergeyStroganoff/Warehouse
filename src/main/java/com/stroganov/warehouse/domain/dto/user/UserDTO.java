package com.stroganov.warehouse.domain.dto.user;

import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

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
    @ToString.Exclude
    @NotEmpty(message = "minimum one role must be chosen")
    private Set<AuthoritiesDTO> authorities = new HashSet<>();
    @ToString.Exclude
    private List<WarehouseDTO> warehouseDTOList = new ArrayList<>();

    public UserDTO(String userName, String password, String fullName, String email, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        if (enabled != userDTO.enabled) return false;
        if (!Objects.equals(userName, userDTO.userName)) return false;
        if (!Objects.equals(password, userDTO.password)) return false;
        if (!Objects.equals(fullName, userDTO.fullName)) return false;
        if (!Objects.equals(email, userDTO.email)) return false;
        if (!Objects.equals(authorities, userDTO.authorities)) return false;
        return Objects.equals(warehouseDTOList, userDTO.warehouseDTOList);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        result = 31 * result + (warehouseDTOList != null ? warehouseDTOList.hashCode() : 0);
        return result;
    }
}

