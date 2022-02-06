package com.caiolima.bcontrol.model;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DESPESA")
@NoArgsConstructor
public class Despesa extends RegistroFinanceiro {

    public Despesa(Long id,
                   String descricao,
                   BigDecimal valor,
                   LocalDate data,
                   CategoriaDespesa categoria) {

        super(id, descricao, valor, data);
        this.categoria = categoria;
    }

    public Despesa(String descricao,
                   BigDecimal valor,
                   LocalDate data,
                   CategoriaDespesa categoria) {
        super(descricao, valor, data);
        this.categoria = categoria;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "CATEGORIA")
    private CategoriaDespesa categoria;

    public CategoriaDespesa getCategoria() {
        return categoria;
    }
}
