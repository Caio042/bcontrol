package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Usuário")
public record UsuarioResponse(
        @Schema(title = "Identificador único",
                description = "Identificador único do usuário",
                example = "52")
        Long id,
        @Schema(title = "Username")
        String username) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername());
    }
}
