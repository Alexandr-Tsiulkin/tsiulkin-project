package com.gmail.alexandr.tsiulkin.service;

import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;

public interface MailService {

    void sendPasswordToEmailAfterAddUser(ShowUserDTO userDTO);

    void sendPasswordToEmailAfterResetPassword(ShowUserDTO userDTO);
}
