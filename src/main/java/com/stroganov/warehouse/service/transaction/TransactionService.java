package com.stroganov.warehouse.service.transaction;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import com.stroganov.warehouse.exception.TransactionServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface TransactionService {
    @Transactional
    int doTransaction(Set<ExelTransactionRowDTO> rowDTOSet, TransactionType transactionType) throws TransactionServiceException;
}
