package com.caiolima.bcontrol.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(title = "Resumo do mês",
        description = "Resumo do mês solicitado com valores e separação por categorias")
public record ResumoDTO(
        @Schema(title = "Valor total da receitas no mês",
                description = "Soma de todas as receitas realizadas no mês",
                example = "2250.50")
        BigDecimal totalReceitas,
        @Schema(title = "Valor total da despesas no mês",
                description = "Soma de todas as despesas realizadas no mês",
                example = "1950,25")
        BigDecimal totalDespesas,
        @Schema(title = "Saldo final do mês",
                description = "Valor disponível após subtrair as despesas das receitas",
                example = "300.25")
        BigDecimal saldoFinal,
        @Schema(title = "Gastos por categoria",
                description = "Lista de valores gastos em cada categoria")
        List<ValoresPorCategoria> gastosPorCategoria,
        @Schema(title = "Recebidos por categoria",
                description = "Lista de valores recebidos em cada categoria")
        List<ValoresPorCategoria> recebidosPorCategoria) {
}
