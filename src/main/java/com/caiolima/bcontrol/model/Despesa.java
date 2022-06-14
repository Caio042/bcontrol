package com.caiolima.bcontrol.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DESPESA")
@NoArgsConstructor
public class Despesa extends RegistroFinanceiro {
    public Despesa(@NonNull String descricao, @NonNull BigDecimal valor, @NonNull LocalDate data, Categoria categoria) {
        super(descricao, valor, data, categoria);
    }
}
