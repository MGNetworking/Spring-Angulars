package com.spring.security.jwt.springsecurityjwt.dao;

import com.spring.security.jwt.springsecurityjwt.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    public AppUser findByUsername(String userName);
}
