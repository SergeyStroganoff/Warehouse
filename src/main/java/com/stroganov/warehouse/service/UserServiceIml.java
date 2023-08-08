package com.stroganov.warehouse.service;


import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class UserServiceIml implements UserService, UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    @Transactional
    public User saveUser(User user) {
        String userPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUserName(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + username + " not found !");
        }
        return userOptional.get();
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
}
