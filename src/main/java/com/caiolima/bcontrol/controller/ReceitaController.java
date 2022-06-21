package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.request.ReceitaRequest;
import com.caiolima.bcontrol.controller.dto.response.ReceitaResponse;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.service.ReceitaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Tag(name = "Receitas", description = "Operações relacionadas a receitas financeiras")
@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService service;

    @Autowired
    public ReceitaController(ReceitaService receitaService) {
        this.service = receitaService;
    }

    @PostMapping
    public ResponseEntity<ReceitaResponse> save(@RequestBody @Valid ReceitaRequest request, UriComponentsBuilder uriBuilder) {
        Receita receita = request.toModel();
        receita = service.save(receita);

        URI uri = uriBuilder.path("/receitas/{id}")
                .buildAndExpand(receita.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(new ReceitaResponse(receita));
    }

    @GetMapping
    public ResponseEntity<List<ReceitaResponse>> listAll(@RequestParam(required = false) String descricao,
                                                         @RequestParam(required = false) Set<Long> categoriaIds,
                                                         @RequestParam(required = false) Integer ano,
                                                         @RequestParam(required = false) Integer mes) {
        List<Receita> receitas = service.listAll(descricao, categoriaIds, ano, mes);
        return ResponseEntity.ok(receitas
                .stream()
                .map(ReceitaResponse::new)
                .sorted(Comparator.comparing(ReceitaResponse::data).reversed())
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaResponse> findById(@PathVariable Long id) {
        Receita receita = service.findById(id);
        return ResponseEntity.ok(new ReceitaResponse(receita));
    }

    @GetMapping("/{ano}/{mes}")
    ResponseEntity<List<ReceitaResponse>> findAllByDate(@PathVariable Integer ano, @PathVariable Integer mes) {
        List<Receita> receitas = service.findAllByDate(ano, mes);

        return ResponseEntity.ok(receitas
                .stream()
                .map(ReceitaResponse::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaResponse> put(@PathVariable Long id, @RequestBody ReceitaRequest request) {
        Receita receita = request.toModel();
        receita.setId(id);
        receita = service.update(receita);
        return ResponseEntity.ok(new ReceitaResponse(receita));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
