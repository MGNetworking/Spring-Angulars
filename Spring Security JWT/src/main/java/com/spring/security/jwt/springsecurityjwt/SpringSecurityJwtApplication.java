package com.spring.security.jwt.springsecurityjwt;

import com.spring.security.jwt.springsecurityjwt.dao.TaskRepository;
import com.spring.security.jwt.springsecurityjwt.entities.AppRole;
import com.spring.security.jwt.springsecurityjwt.entities.AppUser;
import com.spring.security.jwt.springsecurityjwt.entities.Task;
import com.spring.security.jwt.springsecurityjwt.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtApplication implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

    // deviendra un bean Spring
    @Bean
    public BCryptPasswordEncoder getBcryp(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {


        Stream.of("t1","t2","t3").forEach(tk->{
            taskRepository.save(new Task(null,tk));
        });

        taskRepository.findAll().forEach(tks->{
            System.out.println(tks.getTaskName());
        });

        // creation des user
        accountService.saveUser(new AppUser(null, "admin", "123",null));
        accountService.saveUser(new AppUser(null, "user", "123",null));
        accountService.saveUser(new AppUser(null, "max", "123",null));

        // recation des roles
        accountService.saveRole(new AppRole(null,"ADMIN"));
        accountService.saveRole(new AppRole(null,"USER"));

        // ajoute des role au user
        accountService.addRoleToUser("admin","ADMIN");
        accountService.addRoleToUser("admin","USER");
        accountService.addRoleToUser("user","USER");

        accountService.addRoleToUser("max","ADMIN");
        accountService.addRoleToUser("max","USER");
    }
}
