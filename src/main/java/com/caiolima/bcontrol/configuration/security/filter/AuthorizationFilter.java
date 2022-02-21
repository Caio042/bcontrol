package com.caiolima.bcontrol.configuration.security.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.caiolima.bcontrol.configuration.security.JWTService;
import com.caiolima.bcontrol.controller.dto.response.ResponseMessage;
import com.caiolima.bcontrol.exception.InvalidTokenException;
import com.caiolima.bcontrol.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public AuthorizationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isPathIgnored(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            Usuario usuario = jwtService.decodeJWT(authorizationHeader);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (TokenExpiredException | InvalidTokenException e) {
            log.info("Token inválido {}", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            new ObjectMapper().writeValue(response.getOutputStream(), new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
            return;
        } catch (Exception e) {
            log.info("Erro no login {}", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            new ObjectMapper().writeValue(response.getOutputStream(), new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "Não autenticado"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPathIgnored(HttpServletRequest request) {
        return request.getServletPath().equals("/login")
                || request.getServletPath().equals("/usuarios/cadastrar")
                || request.getServletPath().startsWith("/swagger-ui")
                || request.getServletPath().startsWith("/h2-console");
    }
}
