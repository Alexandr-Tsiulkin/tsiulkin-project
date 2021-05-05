package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;

public interface UserService {

    PageDTO getUsersByPage(Integer page);

    void persist(AddUserDTO addUserDTO) throws ServiceException;

    void deleteById(Long id);

    void resetPassword(Long id);

    ShowUserDTO changeRoleById(String roleName, Long id);
}
