package com.gmail.alexandr.tsiulkin.service.converter;

import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;

public interface UserConverter {

    ShowUserDTO convert (User user);

    User convert(AddUserDTO addUserDTO);
}
