package com.caiolima.bcontrol.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DESPESA")
@NoArgsConstructor
public class Despesa extends RegistroFinanceiro {
    public Despesa(Long id, String descricao, BigDecimal valor, LocalDate data) {
        super(id, descricao, valor, data);
    }

    public Despesa(String descricao, BigDecimal valor, LocalDate data) {
        super(descricao, valor, data);
    }
}
