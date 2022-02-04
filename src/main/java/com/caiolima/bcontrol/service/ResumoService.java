package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.controller.dto.GastoPorCategoria;
import com.caiolima.bcontrol.controller.dto.ResumoDTO;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumoService {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ReceitaService receitaService;

    public ResumoDTO generateResumo(Integer ano, Integer mes) {

        List<Despesa> despesas = despesaService.findAllByDate(ano, mes);

        BigDecimal despesaMes = despesas
                .stream()
                .map(RegistroFinanceiro::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal receitaMes = receitaService.getValorNoMes(ano, mes);
        BigDecimal saldoMes = receitaMes.subtract(despesaMes);
        List<GastoPorCategoria> gastosPorCategoria = groupByCategoria(despesas);

        return new ResumoDTO(receitaMes, despesaMes, saldoMes, gastosPorCategoria);
    }

    private List<GastoPorCategoria> groupByCategoria(List<Despesa> despesas) {
        return despesas
                .stream()
                .collect(Collectors
                        .groupingBy(Despesa::getCategoria))
                .entrySet()
                .stream()
                .map(categoriaDespesaListEntry ->
                        new GastoPorCategoria(categoriaDespesaListEntry.getKey(),
                                categoriaDespesaListEntry
                                        .getValue()
                                        .stream()
                                        .map(RegistroFinanceiro::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .toList();
    }
}
