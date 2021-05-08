package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.*;

import java.util.List;

public interface UserService {

    PageDTO getUsersByPage(Integer page);

    void persist(AddUserDTO addUserDTO) throws ServiceException;

    void deleteById(Long id);

    void resetPassword(Long id);

    ShowUserDTO changeRoleById(String roleName, Long id);

    UserDetailsDTO getUserByUserName(String userName);

    UserDetailsDTO changeNameById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeSurnameById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeAddressById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changeTelephoneById(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO changePasswordById(UserDetailsDTO userDetailsDTO) throws ServiceException;
}
