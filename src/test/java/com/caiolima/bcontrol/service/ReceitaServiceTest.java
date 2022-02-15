package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.model.RegistroFinanceiro;
import com.caiolima.bcontrol.repository.ReceitaRepository;
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

import static com.caiolima.bcontrol.helper.TestHelper.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do serviço de receitas")
class ReceitaServiceTest implements WithAssertions {

    @Mock
    private ReceitaRepository repository;
    @InjectMocks
    private ReceitaService service;

    @BeforeAll
    public static void assertJDescriptionConfig() {
        final StringBuilder descriptionReportBuilder = new StringBuilder(String.format("Assertions:%n"));
        Consumer<Description> descriptionConsumer = desc -> descriptionReportBuilder.append(String.format("-- %s%n", desc));

        Assertions.setDescriptionConsumer(descriptionConsumer);
    }

    @Test
    @DisplayName("findById - Deve retornar Receita por id, caso exista")
    void deveRetornarReceitaPorId() {
        Receita receita = receitaSalario(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(receita));

        assertThat(service.findById(1L, null))
                .as("Não deve ser nulo")
                .isNotNull()
                .as("Não deve conter campos nulos")
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .as("Deve ser igual o objeto retornado do repository")
                .isEqualTo(receita);

        assertThatNoException()
                .as("Não deve disparar exception")
                .isThrownBy(() -> service.findById(1L, null));

        verify(repository, times(2)).findById(1L);
    }


    @Test
    @DisplayName("findById - Deve disparar exceção, caso não exista")
    void deveDispararExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .as("Deve disparar exceção caso repository não encontre receita com id")
                .isThrownBy(() -> service.findById(99L, null))
                .as("Deve ter a mensagem certa")
                .withMessage("Registro não encontrado");

        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("save - Deve salvar")
    void deveSalvar() {
        Receita receita = receitaSalario();
        Receita receitaSalva = receitaSalario(1L);
        when(repository.save(receita)).thenReturn(receitaSalva);
        when(repository.isDuplicate(any(), any(), any(), any())).thenReturn(false);

        assertThat(service.save(receita))
                .as("Não deve ser nulo")
                .isNotNull()
                .as("Não deve ter campos nulos")
                .hasNoNullFieldsOrProperties()
                .as("Deve salvar id")
                .returns(1L, RegistroFinanceiro::getId)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .as("Deve ter os campos iguais ao objeto passado")
                .isEqualTo(receita);

        verify(repository, times(1)).save(receita);
        verify(repository, times(1)).isDuplicate(any(),any(), any(), any());
    }

    @Test
    @DisplayName("save - Não deve salvar, caso seja duplicado")
    void testaReceitaDuplicada() {
        when(repository.isDuplicate(any(), any(), any(), any())).thenReturn(true);

        Receita receita = receitaSalario();

        assertThatExceptionOfType(RegistroDuplicadoException.class)
                .as("Deve disparar exceção caso registro esteja duplicado")
                .isThrownBy(() -> service.save(receita))
                .as("Com a mensagem certa")
                .withMessage("Registro já feito no mesmo mês, com a mesma descrição");

        verify(repository, times(1)).isDuplicate(any(),any(), any(), any());
        verify(repository, times(0)).save(any());
    }

    @Test
    @DisplayName("update - não deve atualizar, se não existir registro")
    void testaAtualizacaoEmRegistroInexistente() {
        when(repository.existsById(99L)).thenReturn(false);
        Receita receita = receitaSalario(99L);

        assertThatExceptionOfType(NotFoundException.class)
                .as("Deve disparar exceção caso repository não encontre receita com id")
                .isThrownBy(() -> service.update(receita, null))
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

        Receita receita = receitaSalario(1L);

        assertThatExceptionOfType(RegistroDuplicadoException.class)
                .as("Deve disparar exceção caso registro esteja duplicado")
                .isThrownBy(() -> service.update(receita, null))
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
                .isThrownBy(() -> service.deleteById(99L, null))
                .as("Com a mensagem certa")
                .withMessage("Registro não encontrado");

        verify(repository, times(1)).existsById(99L);
        verify(repository, times(0)).deleteById(99L);
    }
}