package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeUserRoleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;

import java.util.List;

public interface UserService {

    List<ShowUserDTO> getAllUsers(int page);

    void persist(AddUserDTO addUserDTO);

    Long getCountUsers();

    void deleteById(Long id);

    void resetPassword(Long id);

    void changeRoleById(ChangeUserRoleDTO changeUserRoleDTO);
}
