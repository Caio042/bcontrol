package com.caiolima.bcontrol.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Despesa extends RegistroFinanceiro {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "CATEGORIA")
    private CategoriaDespesa categoria;

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

    public Despesa(String descricao, BigDecimal valor, LocalDate data, CategoriaDespesa categoria, Usuario usuario) {
        super(descricao, valor, data, usuario);
        this.categoria = categoria;
    }

    public CategoriaDespesa getCategoria() {
        return categoria;
    }
}
