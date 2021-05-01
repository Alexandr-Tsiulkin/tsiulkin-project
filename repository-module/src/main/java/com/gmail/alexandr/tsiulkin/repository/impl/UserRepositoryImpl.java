package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User findUserByUsername(String email) {
        String stringQuery = "SELECT u FROM User as u WHERE u.email=:email";
        Query query = entityManager.createQuery(stringQuery);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public Long getCountUsers() {
        String hql = "SELECT count(u.id) FROM User as u";
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(int startPosition, int maximumUsersOnPage) {
        String hql = "select u from User as u order by u.email asc";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(maximumUsersOnPage);
        return query.getResultList();
    }
}
