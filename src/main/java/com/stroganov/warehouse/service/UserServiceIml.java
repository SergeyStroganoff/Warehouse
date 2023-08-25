package com.stroganov.warehouse.service;


import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
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
@Transactional
//@RequiredArgsConstructor
@PackagePrivate
public class UserServiceIml implements UserService, UserDetailsService {

    public static final String ERROR_DELETING_USER_WITH_USER_NAME = "Error deleting user with user name: ";
   // @Autowired
    private BCryptPasswordEncoder passwordEncoder;

  //  @Autowired
    private UserRepository userRepository;

  //  @Autowired
    private ModelMapper modelMapper;

  //  @Autowired
    private Logger logger;


    @Override
    @Transactional(readOnly = true)
    /**
     User DetailService method
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userName + " not found !"));
    }
    @Transactional
    public String save(UserDTO userDTO) throws RepositoryTransactionException {
        if (userRepository.findUserByUserName(userDTO.getUserName()).isPresent()) {
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
        return user.getUsername();
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) throws RepositoryTransactionException {
        if (userRepository.findUserByUserName(userDTO.getUserName()).isEmpty()) {
            throw new UsernameNotFoundException("User with the  name: " + userDTO.getUserName() + " was not found");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        try {
            userRepository.update(encodedPassword, userDTO.getFullName(), userDTO.getEmail(), userDTO.isEnabled(), userDTO.getAuthorities(), userDTO.getUserName());
        } catch (Exception e) {
            logger.error("Error updating user with user name: " + userDTO.getUserName(), e);
            throw new RepositoryTransactionException("Error updating user with user name: " + userDTO.getUserName(), e);
        }
    }

    @Transactional
    public void delete(String userName) throws RepositoryTransactionException {
        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + userName + " not found !"));
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            logger.error(ERROR_DELETING_USER_WITH_USER_NAME + userName, e);
            throw new RepositoryTransactionException(ERROR_DELETING_USER_WITH_USER_NAME + user.getUsername(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByName(String userName) {
        Optional<User> userOptional = userRepository.findUserByUserName(userName);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        UserDTO userDTO = modelMapper.map(userOptional.get(), UserDTO.class);
        return Optional.of(userDTO);
    }
}
