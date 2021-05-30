package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.config.BaseIT;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.RoleDTOEnum;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.USERS_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAPIControllerIT extends BaseIT {

    @Test
    void shouldAddUserAndReturn_201() {
        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setLastName("Lastname");
        addUserDTO.setFirstName("Firstname");
        addUserDTO.setMiddleName("Middlename");
        addUserDTO.setEmail("test@gmail.com");
        addUserDTO.setRole(RoleDTOEnum.ADMINISTRATOR);
        addUserDTO.setAddress("Address");
        addUserDTO.setTelephone("61651651654");
        HttpEntity<AddUserDTO> httpEntity = new HttpEntity<>(addUserDTO);
        ResponseEntity<AddUserDTO> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + USERS_PATH,
                        HttpMethod.POST,
                        httpEntity,
                        AddUserDTO.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}