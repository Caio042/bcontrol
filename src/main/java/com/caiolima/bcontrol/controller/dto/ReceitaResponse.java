package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.Receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaResponse(Long id,
                              String descricao,
                              BigDecimal valor,
                              LocalDate data) {

    public ReceitaResponse(Receita receita) {
        this(receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData());
    }
}
