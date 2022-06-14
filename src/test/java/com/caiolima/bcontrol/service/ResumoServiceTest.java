/*package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.controller.dto.response.ValoresPorCategoria;
import com.caiolima.bcontrol.controller.dto.response.ResumoDTO;
import com.caiolima.bcontrol.model.Despesa;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.caiolima.bcontrol.helper.TestHelper.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do serviço de resumo")
class ResumoServiceTest implements WithAssertions {

    @Mock
    private DespesaService despesaService;
    @Mock
    private ReceitaService receitaService;
    @InjectMocks
    private ResumoService service;

    @Test
    @DisplayName("Deve gerar resumo de fevereiro")
    void deveGerarResumoDeFevereiro() {
        List<Despesa> despesas = List.of(despesaAluguel(1L), despesaMercado(2L));
        when(despesaService.findAllByDate(2022, 2)).thenReturn(despesas);
        when(receitaService.getValorNoMes(2022,2)).thenReturn(BigDecimal.valueOf(2500.50));

        ResumoDTO resumoDTO = service.generateResumo(2022, 2);

        assertThat(resumoDTO)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting(ResumoDTO::totalReceitas,
                        ResumoDTO::totalDespesas,
                        ResumoDTO::saldoFinal)
                .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .containsExactly(BigDecimal.valueOf(2500.50),
                        BigDecimal.valueOf(2291),
                        BigDecimal.valueOf(209.5));

        assertThat(resumoDTO.gastosPorCategoria())
                .hasSize(2)
                .extracting(ValoresPorCategoria::categoria, ValoresPorCategoria::valor)
                .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .containsExactlyInAnyOrder(tuple(CategoriaDespesa.ALIMENTACAO, new BigDecimal("790.50")),
                        tuple(CategoriaDespesa.MORADIA, new BigDecimal("1500.50")));
    }

    @Test
    @DisplayName("Deve gerar total negativo, caso despesas seja maior que receitas")
    void deveGerarTotalNegativo() {
        when(despesaService.findAllByDate(2022, 3)).thenReturn(List.of(despesaAluguelMarco(3L)));
        when(receitaService.getValorNoMes(2022,3)).thenReturn(BigDecimal.valueOf(2000));

        ResumoDTO resumoDTO = service.generateResumo(2022, 3);

        assertThat(resumoDTO)
                .extracting(ResumoDTO::totalReceitas,
                        ResumoDTO::totalDespesas,
                        ResumoDTO::saldoFinal)
                .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .containsExactly(BigDecimal.valueOf(2000),
                        BigDecimal.valueOf(2500.5),
                        BigDecimal.valueOf(-500.5));
    }
}
*/