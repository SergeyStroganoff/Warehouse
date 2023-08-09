package com.stroganov.warehouse.service;


import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PackagePrivate
public class UserServiceIml implements UserService, UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public Logger logger;


    @Override
    @Transactional
    /*
    User DetailService method
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userName + " not found !"));
    }


    @Transactional
    public UserDTO save(UserDTO userDTO) throws RepositoryTransactionException {
        if (userRepository.findUserByUserName(userDTO.userName()).isPresent()) {
            throw new ValidationException("User with the same name exists!");
        }
        User user = modelMapper.map(userDTO, User.class);
        String userPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);
        user.setPassword(encodedPassword);
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user with user name: " + user.getUsername(), e);
            throw new RepositoryTransactionException("Error saving user with user name: " + user.getUsername(), e);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) throws RepositoryTransactionException {
        if (userRepository.findUserByUserName(userDTO.userName()).isEmpty()) {
            throw new UsernameNotFoundException("User with the  name: " + userDTO.userName() + " was not found");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.password());
        try {
            userRepository.update(encodedPassword, userDTO.fullName(), userDTO.email(), userDTO.enabled(), userDTO.authorities(), userDTO.userName());
        } catch (Exception e) {
            logger.error("Error updating user with user name: " + userDTO.userName(), e);
            throw new RepositoryTransactionException("Error updating user with user name: " + userDTO.userName(), e);
        }
    }


    @Transactional

    public void delete(String userName) throws RepositoryTransactionException {
        User user =userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userName + " not found !"));
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            logger.error("Error saving user with user name: " + userName, e);
            throw new RepositoryTransactionException("Error deleting user with user name: " + user.getUsername(), e);
        }
    }
}
