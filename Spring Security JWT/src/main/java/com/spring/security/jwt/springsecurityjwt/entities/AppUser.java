package com.spring.security.jwt.springsecurityjwt.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    // charge les roles du User a chaque apple
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roleCollection = new ArrayList<>();


}
