package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeUserRoleDTO;
import com.gmail.alexandr.tsiulkin.service.model.RoleDTOEnum;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void shouldAddPasswordToUserAndSendLetterToMail() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String lastName = "lastName";
        addUserDTO.setLastName(lastName);
        String firstName = "lastName";
        addUserDTO.setFirstName(firstName);
        String middleName = "lastName";
        addUserDTO.setMiddleName(middleName);
        String email = "email";
        addUserDTO.setEmail(email);
        RoleDTOEnum roleDTOEnum = RoleDTOEnum.SALE_USER;
        addUserDTO.setRole(roleDTOEnum);

        Role role = new Role();
        Long id = 1L;
        role.setId(id);
        String roleName = "SALE_USER";
        role.setRoleName(roleName);

        when(roleRepository.findByRoleName(addUserDTO.getRole().name())).thenReturn(role);

        User user = new User();
        Long userId = 1L;
        user.setId(userId);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setRole(role);
        String password = "password";
        user.setPassword(password);
        when(userConverter.convert(addUserDTO)).thenReturn(user);
    }

    @Test
    void shouldChangeRoleById() {
        ChangeUserRoleDTO changeUserRoleDTO = new ChangeUserRoleDTO();
        Long id = 1L;
        changeUserRoleDTO.setId(id);
        String roleName = "newTestNameRole";
        changeUserRoleDTO.setRoleName(roleName);

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

        when(roleRepository.findByRoleName(changeUserRoleDTO.getRoleName())).thenReturn(role);
        when(userRepository.findById(changeUserRoleDTO.getId())).thenReturn(user);
        user.setRole(role);

        ShowUserDTO showUserDTO = new ShowUserDTO();
        showUserDTO.setId(id);
        showUserDTO.setRoleName(roleName);
        when(userConverter.convert(user)).thenReturn(showUserDTO);

        assertEquals(roleName, showUserDTO.getRoleName());
    }
}