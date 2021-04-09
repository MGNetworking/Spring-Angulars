package com.spring.security.jwt.springsecurityjwt.service;

import com.spring.security.jwt.springsecurityjwt.entities.AppRole;
import com.spring.security.jwt.springsecurityjwt.entities.AppUser;

/**
 * Cette interface permet la gestion des comptes d'acces de l'application
 */
public interface AccountService {

    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);

    void addRoleToUser(String username , String RoleName);
    AppUser findUserByName(String userName);

}
