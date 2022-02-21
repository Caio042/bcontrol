package com.caiolima.bcontrol.controller.dto.request;

import com.caiolima.bcontrol.model.Receita;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Cadastro de receita",
        description = "Modelo para cadastro de nova receita")
public record ReceitaRequest(
        @Schema(title = "Descrição da receita",
                description = "Não pode haver duas receitas com a mesma descrição no mesmo mês",
                maxLength = 255,
                example = "salario")
        @NotBlank
        String descricao,
        @Schema(title = "Valor da receita",
                description = "Valor monetário da receita",
                example = "2200.73")
        @NotNull
        @Min(0)
        BigDecimal valor,
        @Schema(title = "Data da receita",
                description = "Data em que foi realizado a receita, no padrão yyyy-MM-dd",
                example = "2022-02-15")
        @NotNull
        LocalDate data) {
    public Receita toModel() {
        return new Receita(descricao(), valor(), data());
    }
}
