package com.stroganov.warehouse.service.item;

import com.stroganov.warehouse.domain.model.item.Dimension;
import com.stroganov.warehouse.repository.DimensionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DimensionServiceImpl implements DimensionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Logger logger;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Override
    public Optional<Dimension> findDimensionByWidthAndHeightAndDepth(String width, String height, String depth) {
        return dimensionRepository.findDimensionByWidthAndHeightAndDepth(width, height, depth);
    }
}
