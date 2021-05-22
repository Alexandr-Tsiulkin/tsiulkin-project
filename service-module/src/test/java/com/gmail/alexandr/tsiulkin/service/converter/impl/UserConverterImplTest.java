package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.repository.model.UserDetails;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.RoleDTOEnum;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDetailsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserConverterImplTest {

    @InjectMocks
    private UserConverterImpl userConverter;

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightId() {
        User user = new User();
        Long id = 1L;
        user.setId(id);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(id, showUserDTO.getId());
    }

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightLastName() {
        User user = new User();
        String lastName = "test last name";
        user.setLastName(lastName);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(lastName, showUserDTO.getLastName());
    }

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightFirstName() {
        User user = new User();
        String firstName = "test first name";
        user.setFirstName(firstName);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(firstName, showUserDTO.getFirstName());
    }

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightMiddleName() {
        User user = new User();
        String middleName = "test middle name";
        user.setMiddleName(middleName);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(middleName, showUserDTO.getMiddleName());
    }

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightEmail() {
        User user = new User();
        String email = "test email";
        user.setEmail(email);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(email, showUserDTO.getEmail());
    }

    @Test
    void shouldConvertUserToShowUserDTOAndReturnRightRoleName() {
        Role role = new Role();
        String roleName = RoleDTOEnum.ADMINISTRATOR.name();
        role.setRoleName(roleName);
        User user = new User();
        user.setRole(role);
        ShowUserDTO showUserDTO = userConverter.convert(user);

        assertEquals(roleName, showUserDTO.getRoleName());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightLastName() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String lastName = "test last name";
        addUserDTO.setLastName(lastName);
        User user = userConverter.convert(addUserDTO);

        assertEquals(lastName, user.getLastName());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightFirstName() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String firstName = "test first name";
        addUserDTO.setFirstName(firstName);
        User user = userConverter.convert(addUserDTO);

        assertEquals(firstName, user.getFirstName());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightMiddleName() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String middleName = "test middle name";
        addUserDTO.setMiddleName(middleName);
        User user = userConverter.convert(addUserDTO);

        assertEquals(middleName, user.getMiddleName());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightEmail() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String email = "test email";
        addUserDTO.setEmail(email);
        User user = userConverter.convert(addUserDTO);

        assertEquals(email, user.getEmail());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightAddress() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String address = "test address";
        addUserDTO.setAddress(address);
        User user = userConverter.convert(addUserDTO);

        assertEquals(address, user.getUserDetails().getAddress());
    }

    @Test
    void shouldConvertAddUserDTOToUserAndReturnRightTelephone() {
        AddUserDTO addUserDTO = new AddUserDTO();
        String telephone = "test telephone";
        addUserDTO.setTelephone(telephone);
        User user = userConverter.convert(addUserDTO);

        assertEquals(telephone, user.getUserDetails().getTelephone());
    }

    @Test
    void shouldConvertUserToUserDetailsDTOAndReturnRightId() {
        User user = new User();
        Long id = 1L;
        user.setId(id);
        ShowUserDetailsDTO showUserDetailsDTO = userConverter.convertUserToUserDetailsDTO(user);

        assertEquals(id, showUserDetailsDTO.getId());
    }

    @Test
    void shouldConvertUserToUserDetailsDTOAndReturnRightFirstName() {
        User user = new User();
        String firstName = "test first name";
        user.setFirstName(firstName);
        ShowUserDetailsDTO showUserDetailsDTO = userConverter.convertUserToUserDetailsDTO(user);

        assertEquals(firstName, showUserDetailsDTO.getFirstName());
    }

    @Test
    void shouldConvertUserToUserDetailsDTOAndReturnRightLastName() {
        User user = new User();
        String lastName = "test last name";
        user.setLastName(lastName);
        ShowUserDetailsDTO showUserDetailsDTO = userConverter.convertUserToUserDetailsDTO(user);

        assertEquals(lastName, showUserDetailsDTO.getLastName());
    }

    @Test
    void shouldConvertUserToUserDetailsDTOAndReturnRightAddress() {
        UserDetails userDetails = new UserDetails();
        String address = "test address";
        userDetails.setAddress(address);
        User user = new User();
        user.setUserDetails(userDetails);
        ShowUserDetailsDTO showUserDetailsDTO = userConverter.convertUserToUserDetailsDTO(user);

        assertEquals(address, showUserDetailsDTO.getAddress());
    }

    @Test
    void shouldConvertUserToUserDetailsDTOAndReturnRightTelephone() {
        UserDetails userDetails = new UserDetails();
        String telephone = "test telephone";
        userDetails.setTelephone(telephone);
        User user = new User();
        user.setUserDetails(userDetails);
        ShowUserDetailsDTO showUserDetailsDTO = userConverter.convertUserToUserDetailsDTO(user);

        assertEquals(telephone, showUserDetailsDTO.getTelephone());
    }
}