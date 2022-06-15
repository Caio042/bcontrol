package com.caiolima.bcontrol.controller.dto.response;
import com.caiolima.bcontrol.model.Receita;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(title = "Receita",
        description = "Receita retornada nas solicitações")
public record ReceitaResponse(
        @Schema(title = "Identificador único",
                description = "Identificador único da receita, " +
                        "pode ser utilizado para solicitar, deletar ou alterar um registro existente",
                example = "318")
        Long id,
        @Schema(title = "Descrição da receita",
                example = "salario")
        String descricao,
        @Schema(title = "Valor da receita",
                description = "Valor monetário da receita",
                example = "2200.50")
        BigDecimal valor,
        @Schema(title = "Data da receita",
                description = "Data em que foi realizado a receita",
                example = "15/02/2022")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate data,
        CategoriaResponse categoria) {

    public ReceitaResponse(Receita receita) {
        this(receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData(),
                new CategoriaResponse(receita.getCategoria()));
    }
}
