package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.CategoriaDespesa;
import com.caiolima.bcontrol.model.Despesa;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Despesa",
        description = "Despesa retornada nas solicitações")
public record DespesaResponse(
        @Schema(title = "Identificador único",
                description = "Identificador único da despesa, " +
                        "pode ser utilizado para solicitar, deletar ou alterar um registro existente",
                example = "318")
        Long id,
        @Schema(title = "Descrição da despesa",
                example = "mercado")
        String descricao,
        @Schema(title = "Valor da despesa",
                description = "Valor monetário da despesa",
                example = "950.25")
        BigDecimal valor,
        @Schema(title = "Data da despesa",
                description = "Data em que foi realizado a despesa, no padrão yyyy-MM-dd",
                example = "2022-02-15")
        LocalDate data,
        @Schema(title = "Categoria da despesa",
                description = "Categoria da despesa",
                example = "MORADIA",
                defaultValue = "OUTRAS")
        CategoriaDespesa categoria) {

    public DespesaResponse(Despesa despesa) {
        this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria());
    }
}
