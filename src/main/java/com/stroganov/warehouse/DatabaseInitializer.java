package com.stroganov.warehouse;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.service.UserService;
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
            Authorities authorities = new Authorities("ROLE_USER");
            WarehouseDTO warehouse = new WarehouseDTO("test warehouse", "1419 W Fullerton Ave, Chicago, IL 60614");
            UserDTO userDTO = new UserDTO("test", "test", "user for test", "test@test.com", true);
            userDTO.getAuthorities().add(authorities);
            userDTO.getWarehouseDTOList().add(warehouse);
            User user = modelMapper.map(userDTO, User.class);
            //userList.add(user);
            if (userService.findUserByName(user.getUsername()).isEmpty()) {
                try {
                    userService.save(userDTO);
                } catch (RepositoryTransactionException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
