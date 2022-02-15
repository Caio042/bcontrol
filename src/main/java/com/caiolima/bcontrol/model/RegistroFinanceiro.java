package com.caiolima.bcontrol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@MappedSuperclass
public abstract class RegistroFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @NonNull
    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    @NonNull
    @Column(name = "DATA", nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    protected RegistroFinanceiro(Long id, @NonNull String descricao, @NonNull BigDecimal valor, @NonNull LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
