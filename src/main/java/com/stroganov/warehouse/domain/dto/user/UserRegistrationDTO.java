package com.stroganov.warehouse.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDTO extends UserDTO {
    @NotBlank(message = "Warehouse name can not be empty")
    private String warehouseName;

    @Size(min = 6, max = 35, message = "Address must be from 6 to 35 symbols")
    private String address;

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "userName=" + this.getUserName() + '\'' +
                "fullName=" + this.getFullName() + '\'' +
                "password=" + this.getPassword() + '\'' +
                "Address=" + this.getAddress() + '\'' +
                "Email=" + this.getEmail() + '\'' +
                "warehouseName='" + warehouseName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
