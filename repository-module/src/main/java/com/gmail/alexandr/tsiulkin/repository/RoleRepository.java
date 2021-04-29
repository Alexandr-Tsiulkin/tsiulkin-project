package com.gmail.alexandr.tsiulkin.repository;

import com.gmail.alexandr.tsiulkin.repository.model.Role;

public interface RoleRepository extends GenericRepository<Long, Role> {

    Role findByRoleName(String roleName);
}
