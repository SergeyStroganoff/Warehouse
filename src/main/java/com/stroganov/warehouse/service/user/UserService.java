package com.stroganov.warehouse.service.user;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.exception.UserNotExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String save(UserDTO userDTO) throws RepositoryTransactionException;

    void update(UserDTO userDTO) throws RepositoryTransactionException;

    void delete(String userName) throws RepositoryTransactionException;

    Optional<UserDTO> findUserByName(String userName);

    Object getAuthenticatedUser();

    List<UserDTO> getAllConnectedUsers(String userName);

    void changeUserStatus(String userName) throws UserNotExistException;
}
