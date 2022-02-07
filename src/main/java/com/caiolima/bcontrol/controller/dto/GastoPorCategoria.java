package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.CategoriaDespesa;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(title = "Gastos por categoria",
        description = "Valores gastos em cada categoria")
public record GastoPorCategoria(
        @Schema(title = "Categoria da despesa",
                description = "Categoria da despesa",
                example = "MORADIA")
        CategoriaDespesa categoria,
        @Schema(title = "Valor monetário gasto na categoria",
                description = "Valor gasto nesta categoria",
                example = "950.25")
        BigDecimal valor) {
}
