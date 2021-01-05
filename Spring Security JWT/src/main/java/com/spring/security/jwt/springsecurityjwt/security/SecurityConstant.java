package com.spring.security.jwt.springsecurityjwt.security;

public interface SecurityConstant {
    public static final String SECRET= "maxime@ghalem.com";
    public static final long EXIRATION_TIME = 864_000_000;  // 10 jours
    public static final String TOKEN_PREFIX ="Bearer ";     // "Bearer " avec l'espace
    public static final String HEADER_STRING ="Authorization";

}
