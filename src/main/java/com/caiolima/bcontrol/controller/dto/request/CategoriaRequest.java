package com.caiolima.bcontrol.controller.dto.request;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.validation.Color;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public record CategoriaRequest(
        @NotBlank
        String categoria,
        @NonNull
        Tipo tipo,
        @NotBlank
        @Color
        String cor) {

    public Categoria toModel() {
        return new Categoria(categoria(), tipo(), cor());
    }
}
