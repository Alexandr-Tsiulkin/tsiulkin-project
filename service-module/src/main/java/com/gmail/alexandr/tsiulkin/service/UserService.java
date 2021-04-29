package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;

import java.util.List;

public interface UserService {

    List<ShowUserDTO> getAllUsers();

    void persist(AddUserDTO addUserDTO);
}
