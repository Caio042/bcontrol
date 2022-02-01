package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.CategoriaDespesa;
import com.caiolima.bcontrol.model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaResponse(
        Long id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        CategoriaDespesa categoria) {

    public DespesaResponse(Despesa despesa) {
        this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria());
    }
}
