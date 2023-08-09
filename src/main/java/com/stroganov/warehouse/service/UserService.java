package com.stroganov.warehouse.service;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.exception.RepositoryTransactionException;

public interface UserService {
    UserDTO save(UserDTO userDTO) throws RepositoryTransactionException;

    void update(UserDTO userDTO) throws RepositoryTransactionException;

    void delete(String userName) throws RepositoryTransactionException;
}
