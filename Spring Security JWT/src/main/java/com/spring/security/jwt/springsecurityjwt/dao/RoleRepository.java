package com.spring.security.jwt.springsecurityjwt.dao;

import com.spring.security.jwt.springsecurityjwt.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

    public AppRole findByRoleName(String roleName);
}
