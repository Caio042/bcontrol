package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.request.CategoriaRequest;
import com.caiolima.bcontrol.controller.dto.response.CategoriaResponse;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Categorias", description = "Operações relacionadas a categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    @Autowired
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrarCategoria(@RequestBody @Valid CategoriaRequest request,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Categoria categoria = service.save(request.toModel());

        URI uri = uriComponentsBuilder.path("/categorias/{id}")
                .buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaResponse(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listAll(@RequestParam Tipo tipo) {
        List<Categoria> categorias = service.listAll(tipo);

        return ResponseEntity.ok(categorias
                .stream()
                .map(CategoriaResponse::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> put(@PathVariable Long id,
                                                 @RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = request.toModel();
        categoria.setId(id);
        categoria = service.update(categoria);

        return ResponseEntity.ok(new CategoriaResponse(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
