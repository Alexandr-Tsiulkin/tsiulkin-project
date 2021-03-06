package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserConverter userConverter;

    @Test
    void shouldChangeRoleById() {
        Long id = 1L;
        String roleName = "newTestNameRole";

        Role role = new Role();
        role.setId(2L);
        role.setRoleName(roleName);

        User user = new User();
        user.setId(id);
        String oldRoleName = "oldTestNameRole";
        Role roleTwo = new Role();
        roleTwo.setId(3L);
        roleTwo.setRoleName(oldRoleName);
        user.setRole(roleTwo);

        when(roleRepository.findByRoleName(roleName)).thenReturn(role);
        when(userRepository.findById(id)).thenReturn(user);
        user.setRole(role);

        ShowUserDTO showUserDTO = new ShowUserDTO();
        showUserDTO.setId(id);
        showUserDTO.setRoleName(roleName);
        when(userConverter.convert(user)).thenReturn(showUserDTO);

        assertEquals(roleName, showUserDTO.getRoleName());
    }
}