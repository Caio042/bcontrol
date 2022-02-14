package com.caiolima.bcontrol.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.caiolima.bcontrol.controller.dto.TokenResponse;
import com.caiolima.bcontrol.exception.InvalidTokenException;
import com.caiolima.bcontrol.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access}")
    private Long accessExpiration;

    @Value("${jwt.expiration.refresh}")
    private Long refreshExpiration;

    private static final String ISSUER = "Budget Control";

    private static final String AUTHENTICATION_TYPE = "Bearer ";

    public TokenResponse generateToken(Usuario usuario) {
        String accessToken = generateAccessToken(usuario);
        String refreshToken = generateRefreshToken(usuario);
        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateAccessToken(Usuario usuario) {
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessExpiration))
                .withIssuer(ISSUER)
                .withClaim("roles", getClaims(usuario))
                .sign(algorithm());
    }

    private String generateRefreshToken(Usuario usuario) {
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration))
                .withIssuer(ISSUER)
                .sign(algorithm());
    }

    private List<String> getClaims(Usuario usuario) {
        return usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public Usuario decodeJWT(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_TYPE)) {
            throw new InvalidTokenException();
        }
        String token = authorizationHeader.substring(AUTHENTICATION_TYPE.length());
        JWTVerifier verifier = JWT.require(algorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        return new Usuario(username);
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }
}


