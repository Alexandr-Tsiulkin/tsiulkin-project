package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.User;

public interface UserRepository extends GenericRepository<Long, User> {

    User findUserByUsername(String email);
}
