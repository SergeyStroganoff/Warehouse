package com.stroganov.warehouse.service;

import com.stroganov.warehouse.domain.dto.user.UserDTO;
import com.stroganov.warehouse.domain.dto.warehouse.WarehouseDTO;
import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.exception.RepositoryTransactionException;
import com.stroganov.warehouse.repository.UserRepository;
import com.stroganov.warehouse.service.user.UserServiceIml;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class UserServiceImlUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceIml userService;

    private static UserDTO userDTO;
    private static User user;


    @BeforeAll
    public static void init() {
        Authorities authorities = new Authorities("ROLE_USER");
        WarehouseDTO warehouse = new WarehouseDTO("test warehouse", "1419 W Fullerton Ave, Chicago, IL 60614");
        userDTO = new UserDTO("test", "test", "user for test", "test@test.com", true);
        userDTO.getWarehouseDTOList().add(warehouse);
        Set<Authorities> authoritiesList = Set.of(authorities);
        user = new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getFullName(), userDTO.getEmail(), true, authoritiesList, userDTO.getWarehouseDTOList().stream().map(warehouseDTO -> new Warehouse(1, warehouseDTO.getWarehouseName(), warehouseDTO.getAddress(), new ArrayList<>())).toList());
    }


    @Test
    void findUserByName() {
        // given  - Mock data
        when(userRepository.findUserByUserName(userDTO.getUserName())).thenReturn(Optional.of(user));
        //when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // when - Perform the method call
        Optional<UserDTO> foundUser = userService.getUserDTOByName("test");

        // Verify the result
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo("test");
        // Verify that the repository method was called
        verify(userRepository).findUserByUserName("test");
    }

    @Test
    void save() throws RepositoryTransactionException {
        // given  - Mock data
        when(userRepository.save(user)).thenReturn(user);
        //when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        // when - Perform the method call
        String gottenUserName = userService.save(userDTO);

        // Verify the result
        assertThat(gottenUserName).isEqualTo("test");
        // Verify that the repository method was called
        verify(userRepository).save(user);
    }
}