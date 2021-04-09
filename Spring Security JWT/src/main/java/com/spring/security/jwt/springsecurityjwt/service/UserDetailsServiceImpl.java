package com.spring.security.jwt.springsecurityjwt.service;

import com.spring.security.jwt.springsecurityjwt.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    /**
     * Cette methode est responsable de l'authentification.
     * @param String le userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // recherche de l'utilisateur
        AppUser user = accountService.findUserByName(userName);

        System.out.println("-------- loadUserByUsername");
        // verifie l'existance du user
        if (user == null){
            throw new UsernameNotFoundException(userName);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // ajoute les roles du user a la colleciton de type GrantedAuthority
        user.getRoleCollection().forEach(appRole -> {
            authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
        });



        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
