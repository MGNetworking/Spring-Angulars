package com.spring.security.jwt.springsecurityjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.Access;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("*********JWT Authorization Filter ************");
        System.out.println(request.getMethod());

        /**
         * Access-Control-Allow-Origin => Accès contrôle Autorisation Origine
         * autorisation des demande d'acces de toutes les origines
         */
        response.addHeader("Access-Control-Allow-Origin", "*");

        /**
         *   Access Controle Allow Header => Accès contrôle Autorisation en-tete
         *   Autorise les demandes qui possedant les en-tete suivant.
         */
        response.addHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type, " +
                        "Access-Control-Request-Method, " +
                        "Access-Control-Request-Headers," +
                        "Authorization");

        /**
         * Access-Control-Expose-Headers => Accès contrôle Exposition Autorisation
         * Autorise a envoyer les en-tetes suivant
         */
        response.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin, " +
                        "Access-Control-Allow-Credentials, " +
                        "Authorization");



        // si requete avec option , pas de token a vérifier
        if (request.getMethod().equals("OPTIONS")) {

            System.out.println("Response OK : " + HttpServletResponse.SC_OK);
            response.setStatus(HttpServletResponse.SC_OK);

        } else {

            // recupération de la presence de l'authorisation dans la requete
            String jwt = request.getHeader(SecurityConstant.HEADER_STRING);

            // verification si null ou si jwt ne commence pas par le prefix
            if (jwt == null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {

                System.out.println("TOKEN null ou non present : " + SecurityConstant.TOKEN_PREFIX);
                // on passe au filtre suivant si y en a un , si non Spring reprend la mains
                filterChain.doFilter(request, response);

                return;

            }

            // Claims = réclamation
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody();

            System.out.println("le claims : " + claims);

            // recupération du nom de utilisateur contenu dans le payload nommé sub
            String userName = claims.getSubject();

            System.out.println("le userName : " + userName);

            // recuépration de role , desérialisation dans une Arralist qui contient un map
            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

            System.out.println("l'ArrayListe contenant la map des roles : " + roles);

            // convertion vers une collection de type GrantedAuthority
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // la clef et authority qui a était génére par Spring
            roles.forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                System.out.println("le role : " + r);
            });

            // creation d'un objet contenant les authorisation et le nom de l'utilisateur
            UsernamePasswordAuthenticationToken authenticatedUser =
                    new UsernamePasswordAuthenticationToken(userName, null, authorities);

            System.out.println("Le UsernamePasswordAuthenticationToken : " + authenticatedUser);

            // chargement du context de security de Spring avec le l'authentification par token
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            System.out.println("SecurityContextHolder charger avec l'authenticatedUser");

            // passage vers une filtre si il en existe
            filterChain.doFilter(request, response);
        }


    }
}
