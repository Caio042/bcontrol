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

    public Receita(String descricao, BigDecimal valor, LocalDate data) {
        super(descricao, valor, data);
    }
}
