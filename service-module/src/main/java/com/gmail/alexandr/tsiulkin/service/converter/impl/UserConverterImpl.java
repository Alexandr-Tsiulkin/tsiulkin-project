package com.gmail.alexandr.tsiulkin.service.converter.impl;

import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.repository.model.UserDetails;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.UserDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public ShowUserDTO convert(User user) {
        ShowUserDTO showUserDTO = new ShowUserDTO();
        Long id = user.getId();
        showUserDTO.setId(id);
        String lastName = user.getLastName();
        showUserDTO.setLastName(lastName);
        String firstName = user.getFirstName();
        showUserDTO.setFirstName(firstName);
        String middleName = user.getMiddleName();
        showUserDTO.setMiddleName(middleName);
        String email = user.getEmail();
        showUserDTO.setEmail(email);

        if (Objects.nonNull(user.getRole())) {
            Role role = user.getRole();
            String roleName = role.getRoleName();
            showUserDTO.setRoleName(roleName);
        }
        return showUserDTO;
    }

    @Override
    public User convert(AddUserDTO addUserDTO) {
        User user = new User();
        String lastName = addUserDTO.getLastName();
        user.setLastName(lastName);
        String firstName = addUserDTO.getFirstName();
        user.setFirstName(firstName);
        String middleName = addUserDTO.getMiddleName();
        user.setMiddleName(middleName);
        String email = addUserDTO.getEmail();
        user.setEmail(email);
        UserDetails userDetails = new UserDetails();
        String address = addUserDTO.getAddress();
        userDetails.setAddress(address);
        String telephone = addUserDTO.getTelephone();
        userDetails.setTelephone(telephone);
        userDetails.setUser(user);
        user.setUserDetails(userDetails);
        return user;
    }

    @Override
    public UserDetailsDTO convertUserToUserDetailsDTO(User user) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        Long id = user.getId();
        userDetailsDTO.setId(id);
        String firstName = user.getFirstName();
        userDetailsDTO.setFirstName(firstName);
        String lastName = user.getLastName();
        userDetailsDTO.setLastName(lastName);
        UserDetails userDetails = user.getUserDetails();
        if (Objects.nonNull(userDetails)) {
            String address = userDetails.getAddress();
            userDetailsDTO.setAddress(address);
            String telephone = userDetails.getTelephone();
            userDetailsDTO.setTelephone(telephone);
        }
        return userDetailsDTO;
    }

}
