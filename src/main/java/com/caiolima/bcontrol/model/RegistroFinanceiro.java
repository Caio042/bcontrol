package com.caiolima.bcontrol.model;

import lombok.*;

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
}
