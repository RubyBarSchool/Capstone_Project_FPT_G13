package com.university.fpt.acf.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {
    @Value("${acf.app.jwtSecret}")
    private String jwtSecret;

    @Value("${acf.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication)  {
        //************************************
        // generate token từ authentication
        //************************************
        log.info("successfulAuthentication in CustomAuthenticationFilter");
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.jwtExpirationMs))
                .withIssuedAt(new Date())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority
                        ::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_token;
    }
}
