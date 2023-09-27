package com.stroganov.warehouse.service.transaction;

import com.stroganov.warehouse.domain.model.transaction.Record;

public interface RecordService {
    Record save(Record recordObject);
}
