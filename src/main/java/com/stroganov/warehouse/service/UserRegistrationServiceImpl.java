package com.stroganov.warehouse.service;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.dto.user.UserRegistrationDTO;
import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.user.Role;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
@PackagePrivate
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public Logger logger;

    @Autowired
    private UserService userService;

    @Override
    public void registerNewUser(UserRegistrationDTO userRegistrationDTO) throws RepositoryTransactionException {
        UserDTO userDTO = modelMapper.map(userRegistrationDTO, UserDTO.class);
        WarehouseDTO warehouseDTO = modelMapper.map(userRegistrationDTO, WarehouseDTO.class);
        userDTO.getWarehouseDTOList().add(warehouseDTO);
        Authorities authorities = new Authorities(Role.ROLE_ADMIN.toString());
        userDTO.getAuthorities().add(authorities);
        userDTO.setEnabled(Boolean.TRUE);
        userService.save(userDTO);
    }
}
