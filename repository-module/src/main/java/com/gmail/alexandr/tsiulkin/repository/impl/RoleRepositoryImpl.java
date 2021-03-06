package com.gmail.alexandr.tsiulkin.repository.impl;

import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public Role findByRoleName(String roleName) {
        String stringQuery = "SELECT r FROM Role as r WHERE r.roleName=:roleName";
        Query query = entityManager.createQuery(stringQuery);
        query.setParameter("roleName", roleName);
        return (Role) query.getSingleResult();
    }
}
