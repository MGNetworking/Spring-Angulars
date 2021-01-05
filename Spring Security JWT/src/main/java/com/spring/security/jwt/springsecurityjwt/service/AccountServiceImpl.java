package com.spring.security.jwt.springsecurityjwt.service;

import com.spring.security.jwt.springsecurityjwt.dao.RoleRepository;
import com.spring.security.jwt.springsecurityjwt.dao.UserRepository;
import com.spring.security.jwt.springsecurityjwt.entities.AppUser;
import com.spring.security.jwt.springsecurityjwt.entities.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {

        // encordage du password
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return  userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        AppRole role = roleRepository.findByRoleName(roleName);
        AppUser user = userRepository.findByUsername(username);

        user.getRoleCollection().add(role);

    }

    @Override
    public AppUser findUserByName(String userName) {

        return userRepository.findByUsername(userName);
    }
}
