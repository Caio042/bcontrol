package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.controller.dto.GastoPorCategoria;
import com.caiolima.bcontrol.controller.dto.ResumoDTO;
import com.caiolima.bcontrol.model.CategoriaDespesa;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

@Service
public class ResumoService {

    private final DespesaService despesaService;

    private final ReceitaService receitaService;

    @Autowired
    public ResumoService(DespesaService despesaService, ReceitaService receitaService) {
        this.despesaService = despesaService;
        this.receitaService = receitaService;
    }

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
        Map<CategoriaDespesa, BigDecimal> valoresPorCategoriaMap = despesas
                .stream()
                .collect(Collectors
                        .groupingBy(Despesa::getCategoria,
                                mapping(RegistroFinanceiro::getValor, reducing(BigDecimal.ZERO, BigDecimal::add))));

        return valoresPorCategoriaMap
                .entrySet()
                .stream()
                .map(valorPorCategoria ->
                        new GastoPorCategoria(valorPorCategoria.getKey(),
                        valorPorCategoria.getValue()))
                .toList();
    }
}