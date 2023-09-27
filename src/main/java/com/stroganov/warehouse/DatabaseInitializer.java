package com.stroganov.warehouse;

import com.stroganov.warehouse.domain.dto.user.AuthoritiesDTO;
import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Logger logger;

    @Value("${app.data.initialisation}")
    String isInitialisationEnabled;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isInitialisationEnabled.equals("enabled")) {
            AuthoritiesDTO authorities = new AuthoritiesDTO("ROLE_USER");
            WarehouseDTO warehouse = new WarehouseDTO("test warehouse", "1419 W Fullerton Ave, Chicago, IL 60614");
            UserDTO userDTO = new UserDTO("test", "test", "user for test", "test@test.com", true);
            userDTO.getAuthorities().add(authorities);
            userDTO.getWarehouseDTOList().add(warehouse);
            if (userService.getUserDTOByName(userDTO.getUserName()).isEmpty()) {
                try {
                    userService.save(userDTO);
                } catch (RepositoryTransactionException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
