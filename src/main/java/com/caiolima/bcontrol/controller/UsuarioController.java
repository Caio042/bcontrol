package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.request.UsuarioRequest;
import com.caiolima.bcontrol.controller.dto.response.UsuarioResponse;
import com.caiolima.bcontrol.exception.UnauthorizedException;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@Tag(name = "Usuário", description = "Operações relacionadas a usuário")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = service.save(request.toModel());
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/usuarios")
                .toUriString());
        return ResponseEntity.created(uri).body(new UsuarioResponse(usuario));
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> update(@RequestBody UsuarioRequest request, Principal principal) {
        if (!request.email().equals(principal.getName())) {
            throw new UnauthorizedException();
        }
        Usuario usuario = service.update(request.toModel());
        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(Principal principal) {
        service.delete(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
