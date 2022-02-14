package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Usuario;

public record UsuarioResponse(Long id,
                              String username) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername());
    }
}
