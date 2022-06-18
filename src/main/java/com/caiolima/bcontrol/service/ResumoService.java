package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.controller.dto.response.CategoriaResponse;
import com.caiolima.bcontrol.controller.dto.response.ValoresPorCategoria;
import com.caiolima.bcontrol.controller.dto.response.ResumoDTO;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.Receita;
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
        List<Receita> receitas = receitaService.findAllByDate(ano, mes);

        BigDecimal receitaMes = receitas
                .stream()
                .map(RegistroFinanceiro::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesaMes = despesas
                .stream()
                .map(RegistroFinanceiro::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        BigDecimal saldoMes = receitaMes.subtract(despesaMes);
        List<ValoresPorCategoria> gastosPorCategoria = despesasPorCategoria(despesas);
        List<ValoresPorCategoria> recebidosPorCategoria = receitasPorCategoria(receitas);

        return new ResumoDTO(receitaMes, despesaMes, saldoMes, gastosPorCategoria, recebidosPorCategoria);
    }

    private List<ValoresPorCategoria> despesasPorCategoria(List<Despesa> despesas) {
        Map<Categoria, BigDecimal> valoresPorCategoriaMap = despesas
                .stream()
                .collect(Collectors
                        .groupingBy(Despesa::getCategoria,
                                mapping(RegistroFinanceiro::getValor, reducing(BigDecimal.ZERO, BigDecimal::add))));

        return valoresPorCategoriaMap
                .entrySet()
                .stream()
                .map(valorPorCategoria ->
                        new ValoresPorCategoria(new CategoriaResponse(valorPorCategoria.getKey()),
                        valorPorCategoria.getValue()))
                .toList();
    }

    private List<ValoresPorCategoria> receitasPorCategoria(List<Receita> receitas) {
        Map<Categoria, BigDecimal> valoresPorCategoriaMap = receitas
                .stream()
                .collect(Collectors
                        .groupingBy(Receita::getCategoria,
                                mapping(RegistroFinanceiro::getValor, reducing(BigDecimal.ZERO, BigDecimal::add))));

        return valoresPorCategoriaMap
                .entrySet()
                .stream()
                .map(valorPorCategoria ->
                        new ValoresPorCategoria(new CategoriaResponse(valorPorCategoria.getKey()),
                                valorPorCategoria.getValue()))
                .toList();
    }
}