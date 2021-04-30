package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.constant.PasswordGenerateConstant;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
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

import static com.gmail.alexandr.tsiulkin.service.constant.UserPaginateConstant.MAXIMUM_USERS_ON_PAGE;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final Random random;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserConverter userConverter,
                           JavaMailSender javaMailSender,
                           PasswordEncoder passwordEncoder,
                           Random random) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.random = random;
    }

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
    public void persist(AddUserDTO addUserDTO) {
        String roleName = addUserDTO.getRole().name();
        Role role = roleRepository.findByRoleName(roleName);
        logger.info("role: {}", role);
        if (Objects.nonNull(role)) {
            User user = userConverter.convert(addUserDTO);
            user.setRole(role);
            String randomPassword = generateRandomPassword();
            logger.info("Password: {}", randomPassword);
            String encodePassword = passwordEncoder.encode(randomPassword);
            logger.info("encode password: {}", encodePassword);
            user.setPassword(encodePassword);
            userRepository.persist(user);
            String email = user.getEmail();
            String recipientMail = "tsiulkin.project@gmail.com";
            SimpleMailMessage message = getMailMessage(email, randomPassword, recipientMail);
            javaMailSender.send(message);
        }
    }

    @Override
    @Transactional
    public Long getCountUsers() {
        return userRepository.getCountUsers();
    }

    private SimpleMailMessage getMailMessage(String email, String randomPassword, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your registration password");
        message.setText("Hello, your account " + email + " has been successfully created:" +
                "your password: " + randomPassword);
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
