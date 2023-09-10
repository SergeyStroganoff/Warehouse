package com.stroganov.warehouse.domain.dto.warehouse;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WarehouseDTO {

    private String warehouseName;
    private String address;
    private List<UserDTO> userList = new ArrayList<>();

    public WarehouseDTO(String warehouseName, String address) {
        this.warehouseName = warehouseName;
        this.address = address;
    }
}
