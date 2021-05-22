package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDetailsDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDetailsDTO;

public interface UserService {

    PageDTO getUsersByPage(Integer page);

    ShowUserDTO persist(AddUserDTO addUserDTO) throws ServiceException;

    boolean isDeleteById(Long id);

    ShowUserDTO resetPassword(Long id) throws ServiceException;

    ShowUserDTO changeRoleById(String roleName, Long id) throws ServiceException;

    ShowUserDetailsDTO getUserByUserName(String userName) throws ServiceException;

    ShowUserDetailsDTO changeParameterById(AddUserDetailsDTO addUserDetailsDTO) throws ServiceException;
}
