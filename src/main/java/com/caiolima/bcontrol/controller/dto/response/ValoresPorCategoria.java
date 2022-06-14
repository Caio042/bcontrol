package com.caiolima.bcontrol.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(title = "Valores por categoria",
        description = "Valores gastos ou recebidos em cada categoria")
public record ValoresPorCategoria(
        @Schema(title = "Categoria",
                description = "Categoria",
                example = "MORADIA")
        CategoriaResponse categoria,
        @Schema(title = "Valor monetário gasto ou recebido na categoria",
                description = "Valor gasto ou recebido nesta categoria",
                example = "950.25")
        BigDecimal valor) {
}
