package com.caiolima.bcontrol.controller.dto.request;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.validation.Color;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CategoriaRequest(
        @NotBlank(message = "descricao obrigatória")
        String descricao,
        @NotNull
        Tipo tipo,
        @NotBlank
        @Color
        String cor) {

    public Categoria toModel() {
        return new Categoria(descricao(), tipo(), cor());
    }
}
