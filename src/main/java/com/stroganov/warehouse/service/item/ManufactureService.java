package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Manufacture;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ManufactureService {
    Optional<Manufacture> findByNameAndDescription(String name, String description);

    List<Manufacture> findAllManufacture();
}
