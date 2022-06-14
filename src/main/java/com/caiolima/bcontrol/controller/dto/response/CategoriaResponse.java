package com.caiolima.bcontrol.controller.dto.response;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;

public record CategoriaResponse(Long id, String descricao, Tipo tipo, String cor) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getId(), categoria.getDescricao(), categoria.getTipo(), categoria.getCor());
    }
}
