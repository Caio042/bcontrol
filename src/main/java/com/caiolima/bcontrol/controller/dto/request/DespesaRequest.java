package com.caiolima.bcontrol.controller.dto.request;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;
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
                description = "Data em que foi realizado a despesa",
                example = "15/02/2022")
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate data,
        @Schema(nullable = true,
                title = "Id da categoria",
                description = "Categoria da despesa",
                example = "1")
        Long categoriaId) {

    public Despesa toModel() {
        return new Despesa(descricao(),
                valor(),
                data(),
                new Categoria(categoriaId()));
    }
}
