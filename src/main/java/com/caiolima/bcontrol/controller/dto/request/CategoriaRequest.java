package com.caiolima.bcontrol.controller.dto.request;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;

public record CategoriaRequest(String categoria, Tipo tipo, String cor) {

    public Categoria toModel() {
        return new Categoria(categoria(), tipo(), cor());
    }
}
