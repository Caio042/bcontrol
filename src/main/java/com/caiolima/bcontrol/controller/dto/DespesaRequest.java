package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.CategoriaDespesa;
import com.caiolima.bcontrol.model.Despesa;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Cadastro de despesa",
        description = "Modelo para cadastro de nova despesa")
public record DespesaRequest(
        @Schema(title = "Descrição da despesa",
                description = "Não pode haver duas despesas com a mesma descrição no mesmo mês",
                maxLength = 255,
                example = "mercado")
        @NotBlank
        String descricao,
        @Schema(title = "Valor da despesa",
                description = "Valor monetário da despesa",
                example = "950.25")
        @NotNull
        @Min(0)
        BigDecimal valor,
        @Schema(title = "Data da despesa",
                description = "Data em que foi realizado a despesa, no padrão yyyy-MM-dd",
                example = "2022-02-15")
        @NotNull
        LocalDate data,
        @Schema(nullable = true,
                title = "Categoria da despesa",
                description = "Categoria da despesa, se não for informado será alocada em OUTRAS",
                example = "MORADIA",
                defaultValue = "OUTRAS")
        CategoriaDespesa categoria) {

    public Despesa toModel() {
        return new Despesa(descricao(),
                valor(),
                data(),
                categoria() != null ? categoria() : CategoriaDespesa.OUTRAS);
    }
}
