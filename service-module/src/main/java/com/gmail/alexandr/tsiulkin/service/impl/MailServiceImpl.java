package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.service.MailService;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String recipientMail;

    @Override
    public void sendPasswordToEmailAfterAddUser(ShowUserDTO userDTO) {
        SimpleMailMessage message = getMailMessageForAddUser(userDTO, recipientMail);
        javaMailSender.send(message);
    }

    @Override
    public void sendPasswordToEmailAfterResetPassword(ShowUserDTO userDTO) {
        SimpleMailMessage message = getMailMessageForResetPassword(userDTO, recipientMail);
        javaMailSender.send(message);
    }

    private SimpleMailMessage getMailMessageForAddUser(ShowUserDTO userDTO, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your registration password");
        message.setText(String.format("Hello, your account %s has been successfully created:your password: %s", userDTO.getEmail(), userDTO.getPassword()));
        return message;
    }

    private SimpleMailMessage getMailMessageForResetPassword(ShowUserDTO userDTO, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your new password");
        message.setText(String.format("Hello %s, your password has been successfully reset:your new password: %s", userDTO.getFirstName(), userDTO.getPassword()));
        return message;
    }
}
