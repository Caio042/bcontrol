package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.repository.DespesaRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.WithAssertions;
import org.assertj.core.description.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Consumer;

import static com.caiolima.bcontrol.helper.TestHelper.despesaAluguel;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do serviço de despesas")
class DespesaServiceTest implements WithAssertions {

    @Mock
    private DespesaRepository repository;

    @InjectMocks
    private DespesaService service;

    @BeforeAll
    public static void assertJDescriptionConfig() {
        final StringBuilder descriptionReportBuilder = new StringBuilder(String.format("Assertions:%n"));
        Consumer<Description> descriptionConsumer = desc -> descriptionReportBuilder.append(String.format("-- %s%n", desc));

        Assertions.setDescriptionConsumer(descriptionConsumer);
    }

    void TestaDisparoDeExcecaoSeNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .as("Deve disparar exceção caso repository não encontre receita com id")
                .isThrownBy(() -> service.findById(99L))
                .as("Deve ter a mensagem certa")
                .withMessage("Registro não encontrado");

        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("save - Não deve salvar, caso seja duplicado")
    void testaDespesaDuplicada() {
        when(repository.isDuplicate(any(), any(), any(), any())).thenReturn(true);

        Despesa despesa = despesaAluguel();

        assertThatExceptionOfType(RegistroDuplicadoException.class)
                .as("Deve disparar exceção caso registro esteja duplicado")
                .isThrownBy(() -> service.save(despesa))
                .as("Com a mensagem certa")
                .withMessage("Registro já feito no mesmo mês, com a mesma descrição");

        verify(repository, times(1)).isDuplicate(any(), any(), any(), any());
        verify(repository, times(0)).save(any());
    }

    @Test
    @DisplayName("update - não deve atualizar, se não existir registro")
    void testaAtualizacaoEmRegistroInexistente() {
        when(repository.existsById(99L)).thenReturn(false);
        Despesa despesa = despesaAluguel(99L);

        assertThatExceptionOfType(NotFoundException.class)
                .as("Deve disparar exceção caso repository não encontre despesa com id")
                .isThrownBy(() -> service.update(despesa))
                .as("Com a mensagem certa")
                .withMessage("Registro não encontrado");

        verify(repository, times(0)).save(any());
        verify(repository, times(1)).existsById(99L);
    }

    @Test
    @DisplayName("update - não deve atualizar, se estiver duplicado")
    void testaAtualizacaoEmRegistroDuplicado() {
        when(repository.isDuplicate(any(), any(), any(), any())).thenReturn(true);
        when(repository.existsById(1L)).thenReturn(true);

        Despesa despesa = despesaAluguel(1L);

        assertThatExceptionOfType(RegistroDuplicadoException.class)
                .as("Deve disparar exceção caso registro esteja duplicado")
                .isThrownBy(() -> service.update(despesa))
                .as("Com a mensagem certa")
                .withMessage("Registro já feito no mesmo mês, com a mesma descrição");

        verify(repository, times(0)).save(any());
        verify(repository, times(1)).isDuplicate(any(),any(), any(), any());

    }

    @Test
    @DisplayName("delete - deve disparar Exception, caso registro não exista")
    void testaExclusaoDeRegistroInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatExceptionOfType(NotFoundException.class)
                .as("Deveria disparar exception")
                .isThrownBy(() -> service.deleteById(99L))
                .as("Com a mensagem certa")
                .withMessage("Registro não encontrado");

        verify(repository, times(1)).existsById(99L);
        verify(repository, times(0)).deleteById(99L);
    }
}
