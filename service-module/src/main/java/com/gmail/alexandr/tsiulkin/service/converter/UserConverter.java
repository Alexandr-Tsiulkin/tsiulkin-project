package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;

public interface UserConverter {

    User convert (ShowUserDTO showUserDTO);

    ShowUserDTO convert (User user);

    User convert(AddUserDTO addUserDTO);
}
