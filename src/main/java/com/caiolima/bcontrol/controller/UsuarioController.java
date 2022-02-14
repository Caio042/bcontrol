package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.UsuarioRequest;
import com.caiolima.bcontrol.controller.dto.UsuarioResponse;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioResponse> atualize(@RequestBody UsuarioRequest request) {
        Usuario usuario = service.atualize(request.toModel());
        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
