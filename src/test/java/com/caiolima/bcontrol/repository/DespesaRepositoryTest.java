package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.caiolima.bcontrol.helper.TestHelper.*;

@DataJpaTest
class DespesaRepositoryTest implements WithAssertions {

    @Autowired
    private DespesaRepository repository;

    private static final LocalDate INICIO_DO_MES = LocalDate.of(2022, Month.FEBRUARY,1);
    private static final LocalDate FIM_DO_MES = LocalDate.of(2022, Month.FEBRUARY,28);


    @Test
    @DisplayName("Deve encontrar registros duplicados")
    void deveEncontrarRegistrosDuplicadosDadoPeriodoDeTempo() {
        Despesa despesa = despesaAluguel(50L);
        repository.save(despesa);

        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, 99L)).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros duplicado com mesmo id")
    void deveIgnorarRegistrosDuplicadosComMesmoId() {
        Despesa despesa = despesaAluguel();
        repository.save(despesa);

        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, 1L)).isFalse();
    }

    @Test
    @DisplayName("Deve encontrar registros duplicados dado id nulo")
    void deveEncontrarRegistrosDuplicadoDadoIdNulo() {
        Despesa despesa = despesaAluguel(52L);
        repository.save(despesa);

        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, null)).isTrue();
    }

    @Test
    @DisplayName("Deve ignorar registros com outra descrição ou data")
    void deveIgnorarRegistrosNaoDuplicados() {
        Despesa despesaFreeLanceFev = despesaMercado();
        Despesa despesaAluguelMarco = despesaAluguelMarco();
        repository.saveAll(List.of(despesaFreeLanceFev, despesaAluguelMarco));

        assertThat(repository.isDuplicate("aluguel", INICIO_DO_MES, FIM_DO_MES, null)).isFalse();
    }

    @Test
    @DisplayName("Deve trazer registros de fevereiro")
    void deveTrazerDoisRegistros() {
        Despesa mercado = despesaMercado();
        Despesa aluguel = despesaAluguel();
        Despesa aluguelMarco = despesaAluguelMarco();

        repository.saveAll(List.of(mercado, aluguel, aluguelMarco));


        assertThat(repository.findAllByDate(2022, 2))
                .hasSize(2)
                .extracting(RegistroFinanceiro::getData)
                .doesNotContain(LocalDate.of(2022, Month.MARCH, 2));
    }
}
