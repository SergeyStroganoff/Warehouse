package com.stroganov.warehouse.service.warehouse;

import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.repository.WarehouseRepository;
import com.stroganov.warehouse.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private UserService userService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> getUserWarehouseList() {
        User user = (User) userService.getAuthenticatedUser();
        return user.getWarehouseList();
    }

    @Override
    public Optional<Warehouse> findById(int id) {
        return warehouseRepository.findById(id);
    }
}
