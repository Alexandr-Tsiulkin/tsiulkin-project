package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User findUserByUsername(String email) {
        String stringQuery = "SELECT u FROM User as u WHERE u.email=:email";
        Query query = entityManager.createQuery(stringQuery);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
