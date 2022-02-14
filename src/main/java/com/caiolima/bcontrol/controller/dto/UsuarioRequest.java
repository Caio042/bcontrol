package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Usuario;

import javax.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank
        String username,
        @NotBlank
        String password) {
    public Usuario toModel() {
        return new Usuario(username(), password());
    }
}
