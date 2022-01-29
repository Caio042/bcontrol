package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Receita;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaRequest(
        @NotBlank
        String descricao,
        @NotNull
        @Min(0)
        BigDecimal valor,
        @NotNull
        LocalDate data) {
    public Receita toModel() {
        return new Receita(descricao(), valor(), data());
    }
}
