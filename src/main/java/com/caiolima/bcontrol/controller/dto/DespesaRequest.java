package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Despesa;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaRequest(
        @NotBlank
        String descricao,
        @NotNull
        @Min(0)
        BigDecimal valor,
        @NotNull
        LocalDate data) {

    public Despesa toModel() {
        return new Despesa(descricao(), valor(), data());
    }
}
