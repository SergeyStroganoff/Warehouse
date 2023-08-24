package com.stroganov.warehouse.domain.dto.warehouse;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class WarehouseDTO {

    private String warehouseName;
    private String address;
    private List<UserDTO> userList = new ArrayList<>();

    public WarehouseDTO(String warehouseName, String address) {
        this.warehouseName = warehouseName;
        this.address = address;
    }
}
