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

    void deleteById(Long id);

    ShowUserDTO resetPassword(Long id);

    ShowUserDTO changeRoleById(String roleName, Long id);

    ShowUserDetailsDTO getUserByUserName(String userName);

    void changeParameterById(AddUserDetailsDTO addUserDetailsDTO) throws ServiceException;
}
