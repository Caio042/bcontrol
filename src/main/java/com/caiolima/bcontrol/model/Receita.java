package com.caiolima.bcontrol.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "RECEITA")
@NoArgsConstructor
public class Receita extends RegistroFinanceiro {

    public Receita(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
        super(descricao, valor, data, categoria);
    }

    public Receita(String descricao, BigDecimal valor, LocalDate data, Categoria categoria, Usuario usuario) {
        super(descricao, valor, data, usuario, categoria);
    }
}
