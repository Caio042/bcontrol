/*package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

@DataJpaTest
class DespesaRepositoryTest implements WithAssertions {

    @Autowired
    private DespesaRepository repository;

    private static final LocalDate INICIO_DO_MES = LocalDate.of(2022, Month.FEBRUARY,1);
    private static final LocalDate FIM_DO_MES = LocalDate.of(2022, Month.FEBRUARY,28);

    @Test
    @DisplayName("Deve encontrar registros duplicados")
    void deveEncontrarRegistrosDuplicadosDadoPeriodoDeTempo() {
        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, 99L, "caio")).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros duplicado com mesmo id")
    void deveIgnorarRegistrosDuplicadosComMesmoId() {
        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, 1L, "caio")).isFalse();
    }

    @Test
    @DisplayName("Deve encontrar registros duplicados dado id nulo")
    void deveEncontrarRegistrosDuplicadoDadoIdNulo() {
        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, null, "caio")).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros com outra descrição ou data")
    void deveIgnorarRegistrosNaoDuplicados() {
        assertThat(repository.isDuplicate("roupa", INICIO_DO_MES, FIM_DO_MES, null, "caio")).isFalse();
    }

    @Test
    @DisplayName("Deve trazer registros de fevereiro")
    void deveTrazerDoisRegistros() {
        assertThat(repository.findAllByDate(2022, 2, "caio"))
                .hasSize(2)
                .extracting(RegistroFinanceiro::getData)
                .doesNotContain(LocalDate.of(2022, Month.MARCH, 2));
    }
}


 */