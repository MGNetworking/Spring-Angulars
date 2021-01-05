package com.spring.security.jwt.springsecurityjwt.service;

import com.spring.security.jwt.springsecurityjwt.entities.AppRole;
import com.spring.security.jwt.springsecurityjwt.entities.AppUser;

/**
 * Cette interface permet la gestion des comptes d'acces de l'application
 */
public interface AccountService {

    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);

    public void addRoleToUser(String username , String RoleName);
    public AppUser findUserByName(String userName);

}
