package com.caiolima.bcontrol.helper;

import com.caiolima.bcontrol.model.CategoriaDespesa;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class TestHelper {

    public static Receita receitaSalario() {
        return new Receita("salario",
                new BigDecimal(2000),
                LocalDate.of(2022, Month.FEBRUARY,5),
                usuario(1L));
    }
    public static Receita receitaSalario(Long id) {
        Receita receita = receitaSalario();
        receita.setId(id);
        return receita;
    }

    public static Receita receitaFreeLance() {
        return new Receita( "prestação de serviços",
                new BigDecimal("500.50"),
                LocalDate.of(2022, Month.FEBRUARY, 22),
                usuario(1L));
    }

    public static Receita receitaFreeLance(Long id) {
        Receita receita = receitaFreeLance();
        receita.setId(id);
        return receita;
    }

    public static Despesa despesaMercado() {
        return new Despesa("mercado",
                new BigDecimal("790.50"),
                LocalDate.of(2022, Month.FEBRUARY, 10),
                CategoriaDespesa.ALIMENTACAO,
                usuario(1L));
    }

    public static Despesa despesaMercado(Long id) {
        Despesa despesa = despesaMercado();
        despesa.setId(id);
        return despesa;
    }

    public static Despesa despesaAluguel() {
        return new Despesa("aluguel",
                new BigDecimal("1500.50"),
                LocalDate.of(2022, Month.FEBRUARY, 2),
                CategoriaDespesa.MORADIA,
                usuario(1L));
    }

    public static Despesa despesaAluguel(Long id) {
        Despesa despesa = despesaAluguel();
        despesa.setId(id);
        return despesa;
    }

    public static Receita receitaSalarioMarco() {
        return new Receita("salario",
                new BigDecimal(2000),
                LocalDate.of(2022, Month.MARCH,5),
                usuario(1L));
    }

    public static Receita receitaSalarioMarco(Long id) {
        Receita receita = receitaSalarioMarco();
        receita.setId(id);
        return receita;
    }

    public static Despesa despesaAluguelMarco() {
        return new Despesa("aluguel",
                new BigDecimal("2500.50"),
                LocalDate.of(2022, Month.MARCH, 2),
                CategoriaDespesa.MORADIA,
                usuario(1L));
    }

    public static Despesa despesaAluguelMarco(Long id) {
        Despesa despesa = despesaAluguelMarco();
        despesa.setId(id);
        return despesa;
    }

    public static Usuario usuario() {
        return new Usuario("caio", "1234");
    }

    public static Usuario usuario(Long id) {
        Usuario usuario = usuario();
        usuario.setId(id);
        return usuario;
    }
}
