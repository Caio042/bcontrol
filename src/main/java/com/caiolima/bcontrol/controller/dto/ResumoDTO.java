package com.caiolima.bcontrol.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record ResumoDTO(BigDecimal totalReceitas,
                        BigDecimal totalDespesas,
                        BigDecimal saldoFinal,
                        List<GastoPorCategoria> gastosPorCategoria) {
}
