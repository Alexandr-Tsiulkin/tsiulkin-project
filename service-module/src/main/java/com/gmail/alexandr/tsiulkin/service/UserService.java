package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.*;
import org.springframework.mail.SimpleMailMessage;

public interface UserService {

    PageDTO getUsersByPage(Integer page);

    SimpleMailMessage persist(AddUserDTO addUserDTO) throws ServiceException;

    void deleteById(Long id);

    SimpleMailMessage resetPassword(Long id);

    ShowUserDTO changeRoleById(String roleName, Long id);

    UserDetailsDTO getUserByUserName(String userName);

    UserDetailsDTO changeNameById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeSurnameById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeAddressById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeTelephoneById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changePasswordById(UserDetailsDTO userDetailsDTO) throws ServiceException;

    void addUserAndSendPasswordToEmail(AddUserDTO addUserDTO) throws ServiceException;

    void resetPasswordAndSendToEmail(Long id);
}
