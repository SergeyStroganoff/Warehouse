package com.stroganov.warehouse.service.user;

import com.stroganov.warehouse.domain.dto.user.UserRegistrationDTO;
import com.stroganov.warehouse.exception.RepositoryTransactionException;

public interface UserRegistrationService {

    void registerNewUser(UserRegistrationDTO userRegistrationDTO) throws RepositoryTransactionException;
}
