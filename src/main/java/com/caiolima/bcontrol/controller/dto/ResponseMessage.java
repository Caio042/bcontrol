package com.caiolima.bcontrol.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Retorno da solicitação",
        description = "Mensagem retornada em solicitações não permitidas")
public record ResponseMessage(
        @Schema(title = "Status HTPP",
                description = "Código do Status HTTP do retorno da solicitação",
                example = "404")
        Integer status,
        @Schema(title = "Mensagem",
                description = "Mensagem retornada para a solicitação",
                example = "Registro não encontrado")
        String message) {
}
