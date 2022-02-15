package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(title = "Cadastro de usuário", description = "Modelo utilizado para criação e autenticação de usuários")
public record UsuarioRequest(
        @Schema(title = "Username",
                description = "Username para identificação do usuário, deve ser único",
                maxLength = 255,
                example = "caio.lima"
        )
        @NotBlank
        String username,
        @Schema(title = "Senha",
                description = "Pode ser composta de letras, números, e caracteres especiais",
                maxLength = 255,
                example = "12345678"
        )
        @NotBlank
        String password) {
    public Usuario toModel() {
        return new Usuario(username(), password());
    }
}
