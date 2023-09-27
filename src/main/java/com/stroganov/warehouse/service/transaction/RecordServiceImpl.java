package com.stroganov.warehouse.service.transaction;

import com.stroganov.warehouse.domain.model.transaction.Record;
import com.stroganov.warehouse.repository.RecordRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private Logger logger;
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Record save(Record recordObject) {
        return recordRepository.save(recordObject);
    }
}
