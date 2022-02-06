package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.caiolima.bcontrol.helper.TestHelper.*;

@DataJpaTest
class ReceitaRepositoryTest implements WithAssertions {

    @Autowired
    private ReceitaRepository repository;

    private static final LocalDate INICIO_DO_MES = LocalDate.of(2022, Month.FEBRUARY,1);
    private static final LocalDate FIM_DO_MES = LocalDate.of(2022, Month.FEBRUARY,28);

    @Test
    @DisplayName("Deve encontrar registros duplicados")
    void deveEncontrarRegistrosDuplicadosDadoPeriodoDeTempo() {
        Receita receita = receitaSalario(50L);
        repository.save(receita);

        assertThat(repository.isDuplicate("salario", INICIO_DO_MES, FIM_DO_MES, 99L)).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros duplicado com mesmo id")
    void deveIgnorarRegistrosDuplicadosComMesmoId() {
        Receita receita = receitaSalario();
        repository.save(receita);

        assertThat(repository.isDuplicate("salario", INICIO_DO_MES, FIM_DO_MES, 1L)).isFalse();
    }

    @Test
    @DisplayName("Deve encontrar registros duplicados dado id nulo")
    void deveEncontrarRegistrosDuplicadoDadoIdNulo() {
        Receita receita = receitaSalario(52L);
        repository.save(receita);

        assertThat(repository.isDuplicate("salario", INICIO_DO_MES, FIM_DO_MES, null)).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros com outra descrição ou data")
    void deveIgnorarRegistrosNaoDuplicados() {
        Receita receitaFreeLanceFev = receitaFreeLance();
        Receita receitaSalarioMarco = receitaSalarioMarco();
        repository.saveAll(List.of(receitaFreeLanceFev, receitaSalarioMarco));

        assertThat(repository.isDuplicate("salario", INICIO_DO_MES, FIM_DO_MES, null)).isFalse();
    }

    @Test
    @DisplayName("Deve somar o valor no mês")
    void deveSomarValorNoMes() {
        Receita receitaFreeLance = receitaFreeLance();
        Receita receitaSalario = receitaSalario();
        repository.saveAll(List.of(receitaFreeLance, receitaSalario));

        assertThat(repository.getValorNoMes(2022, 2))
                .isNotEmpty()
                .get()
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal("2500.50"));
    }

    @Test
    @DisplayName("Deve trazer registros de fevereiro")
    void deveTrazerDoisRegistros() {
        Receita receitaFreeLance = receitaFreeLance();
        Receita receitaSalario = receitaSalario();
        Receita salarioMarco = receitaSalarioMarco();

        repository.saveAll(List.of(receitaFreeLance, receitaSalario, salarioMarco));


        assertThat(repository.findAllByDate(2022, 2))
                .hasSize(2)
                .extracting(RegistroFinanceiro::getData)
                .doesNotContain(LocalDate.of(2022, Month.MARCH, 5));
    }
}
