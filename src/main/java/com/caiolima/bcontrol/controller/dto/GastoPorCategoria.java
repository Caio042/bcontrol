package com.caiolima.bcontrol.controller.dto;

import com.caiolima.bcontrol.model.CategoriaDespesa;

import java.math.BigDecimal;

public record GastoPorCategoria(CategoriaDespesa categoria,
                                BigDecimal valor) {
}
