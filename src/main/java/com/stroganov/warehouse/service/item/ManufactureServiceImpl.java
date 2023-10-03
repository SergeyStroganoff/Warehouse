package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Manufacture;
import com.stroganov.warehouse.repository.ManufactureRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManufactureServiceImpl implements ManufactureService {
    @Autowired
    Logger logger;

    @Autowired
    ManufactureRepository manufactureRepository;

    @Override
    public Optional<Manufacture> findByNameAndDescription(String name, String description) {
        return manufactureRepository.findByNameAndDescription(name, description);
    }

    @Override
    public List<Manufacture> findAllManufacture() {
        return manufactureRepository.findAll();
    }
}
