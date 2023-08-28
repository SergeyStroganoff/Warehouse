package com.stroganov.warehouse.service;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.exception.RepositoryTransactionException;

import java.util.Optional;

public interface UserService {
    String save(UserDTO userDTO) throws RepositoryTransactionException;

    void update(UserDTO userDTO) throws RepositoryTransactionException;

    void delete(String userName) throws RepositoryTransactionException;

    Optional<UserDTO> findUserByName(String userName);

    Object getAuthenticatedUser();
}
