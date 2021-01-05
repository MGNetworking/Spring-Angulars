package com.spring.security.jwt.springsecurityjwt.security;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // authentification par un service
    @Autowired
    private UserDetailsService userDetailsService;

    // fonction d'encodage
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(this.bCryptPasswordEncoder);

    }

    /**
     * Spring security en mode STATELESS.
     * Systeme d'authentification par valeur
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // desactivation du formulaire d'authetification générer par Spring security
        //http.formLogin().loginPage("/maPageLogin");

        // desactivation la syncronisation des token
        http.csrf().disable();

        // desativation des session, en mode STATELESS
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // parti a login ne néssecite pas d'authentification
        http.authorizeRequests()
                .antMatchers("/login/**", "/register/**").permitAll();


        // ajout d'un regle authorisation pour le droit ADMIN et USER
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/tasks/**")
                .hasAnyAuthority("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/tasks/**")
                .hasAnyAuthority("ADMIN","USER");

        // creation d'un filtre
        http.addFilter(new JWTAuthentificationFilter(authenticationManager()));

        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
