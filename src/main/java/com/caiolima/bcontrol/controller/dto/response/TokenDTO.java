package com.caiolima.bcontrol.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Tokens", description = "Tokens utilizados para autenticação")
public record TokenDTO(
        @Schema(title = "Access Token",
                description = "Token criptografado contendo informações de autenticação do usuário")
        String accessToken,
        @Schema(title = "Refresh Token",
                description = "Token utilizado para gerar um novo Access Token após expiração"
        )
        String refreshToken) {
}
