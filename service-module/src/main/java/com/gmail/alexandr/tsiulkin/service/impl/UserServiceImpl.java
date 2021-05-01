package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.constant.PasswordGenerateConstant;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeUserRoleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.MailConstant.RECIPIENT_MAIL;
import static com.gmail.alexandr.tsiulkin.service.constant.UserPaginateConstant.MAXIMUM_USERS_ON_PAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final Random random;

    @Override
    @Transactional
    public List<ShowUserDTO> getAllUsers(int page) {
        int startPosition = (page - 1) * MAXIMUM_USERS_ON_PAGE;
        List<User> users = userRepository.findAll(startPosition, MAXIMUM_USERS_ON_PAGE);
        return users.stream()
                .map(userConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowUserDTO persist(AddUserDTO addUserDTO) {
        String roleName = addUserDTO.getRole().name();
        Role role = roleRepository.findByRoleName(roleName);
        logger.info("role: {}", role);
        User user = new User();
        if (Objects.nonNull(role)) {
            user = userConverter.convert(addUserDTO);
            user.setRole(role);
            String randomPassword = generateRandomPassword();
            logger.info("Password: {}", randomPassword);
            String encodePassword = passwordEncoder.encode(randomPassword);
            logger.info("encode password: {}", encodePassword);
            user.setPassword(encodePassword);
            userRepository.persist(user);
            String email = user.getEmail();
            SimpleMailMessage message = getMailMessageForAddUser(email, randomPassword, RECIPIENT_MAIL);
            javaMailSender.send(message);
        }
        return userConverter.convert(user);
    }

    @Override
    @Transactional
    public Long getCountUsers() {
        return userRepository.getCountUsers();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.removeById(id);
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            String password = user.getPassword();
            logger.info("old password: {}", password);
            String randomPassword = generateRandomPassword();
            String encodePassword = passwordEncoder.encode(randomPassword);
            logger.info("new password: {}", encodePassword);
            user.setPassword(encodePassword);
            userRepository.merge(user);
            String firstName = user.getFirstName();
            SimpleMailMessage message = getMailMessageForResetPassword(firstName, randomPassword, RECIPIENT_MAIL);
            javaMailSender.send(message);
        }
    }

    @Override
    @Transactional
    public ShowUserDTO changeRoleById(ChangeUserRoleDTO changeUserRoleDTO) {
        String newRole = changeUserRoleDTO.getRoleName();
        logger.info("new Role: {}", newRole);
        Role byRoleName = roleRepository.findByRoleName(newRole);
        logger.info("byRoleName: {}", byRoleName);
        User user = userRepository.findById(changeUserRoleDTO.getId());
        logger.info("user: {}", user);
        byRoleName.getUsers().add(user);
        userRepository.merge(user);
        return userConverter.convert(user);
    }

    private SimpleMailMessage getMailMessageForAddUser(String email, String randomPassword, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your registration password");
        message.setText("Hello, your account " + email + " has been successfully created:" +
                "your password: " + randomPassword);
        return message;
    }

    private SimpleMailMessage getMailMessageForResetPassword(String firstName, String randomPassword, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your new password");
        message.setText("Hello " + firstName + ", your password has been successfully reset:" +
                "your new password: " + randomPassword);
        return message;
    }

    private String generateRandomPassword() {
        logger.info("Generating Password");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < PasswordGenerateConstant.NUMBER_OF_CHARS_IN_PASSWORD; i++) {
            int character = (random.nextInt(PasswordGenerateConstant.ALPHA_NUMERIC_STRING.length()));
            builder.append(PasswordGenerateConstant.ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
