package com.spring.security.jwt.springsecurityjwt.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.jwt.springsecurityjwt.entities.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Cette classe est un filtre d'authentification basé sur les tokens
 */
public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger LOGGER = LogManager.getLogger(JWTAuthentificationFilter.class);

    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Cette méthode d'authentificatrion suppose que les données d'authentification son au format JSON.
     * Les données seront serialiser , mapper pour etre transmise a l'objet appUser.
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        AppUser appUser =null;

        System.out.println("********* attemptAuthentication => JWT  AuthentificationFilter Filter *************");

        try{

            System.out.println("lecture de la requete formulaire ");
            System.out.println(request.getMethod());

            // désérilalisation du contenue de la requete reçut en format Json
            appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);

            // si le format est en x-www-form-urlencoded
/*            String username = request.getParameter("username");
            String password = request.getParameter("password");
 */

        }catch (JsonParseException jpe){

            LOGGER.error(jpe);
            System.out.println("JsonParseException ********** "+jpe);
            throw new RuntimeException(jpe);

        }catch (JsonMappingException jme){

            LOGGER.error(jme);
            System.out.println("JsonMappingException ********** "+jme);
            throw new RuntimeException(jme);

        }catch (IOException ioe){

            LOGGER.error(ioe);
            System.out.println("IOException ********** "+ioe);
            throw new RuntimeException(ioe);
        }

        System.out.println("*********************");
        System.out.println("username : " + appUser.getUsername());
        System.out.println("password : " + appUser.getPassword());
        System.out.println("*********************");

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        appUser.getUsername(),
                        appUser.getPassword()));
    }

    /**
     * Dans cette methode est générer le tokens
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        System.out.println("********* successfulAuthentication => JWT  AuthentificationFilter Filter **********");
        System.out.println("authResult.getPrincipal() : " + authResult.getPrincipal());

        // authResult est le resultat de l'authentification est recup l'objet User de Spring
        // qui contient les information sur l'utilisateur qui est authentifier
        User springUser = (User) authResult.getPrincipal();

        System.out.println("username : " + authResult.getName());

        // creation du token
        String jwt = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SecurityConstant.SECRET)
                .claim("roles", springUser.getAuthorities())
                .compact();

        System.out.println(" successfulAuthentication JWT : " + jwt);

        // ajout du token à la response
        response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + jwt);
    }
}
