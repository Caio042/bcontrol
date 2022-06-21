package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.request.DespesaRequest;
import com.caiolima.bcontrol.controller.dto.response.DespesaResponse;

import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.service.DespesaService;

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

@Tag(name = "Despesas", description = "Operações relacionadas a despesas")
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService service;

    @Autowired
    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DespesaResponse> save(@RequestBody @Valid DespesaRequest request,
                                                UriComponentsBuilder uriBuilder) {
        Despesa despesa = request.toModel();
        despesa = service.save(despesa);

        URI uri = uriBuilder.path("/despesas/{id}")
                .buildAndExpand(despesa.getId()).toUri();
        return ResponseEntity.created(uri)
                .body(new DespesaResponse(despesa));
    }

    @GetMapping
    public ResponseEntity<List<DespesaResponse>> listAll(@RequestParam(required = false) String descricao,
                                                         @RequestParam(required = false) Set<Long> categoriaIds,
                                                         @RequestParam(required = false) Integer ano,
                                                         @RequestParam(required = false) Integer mes) {
        List<Despesa> despesas = service.listAll(descricao, categoriaIds, ano, mes);
        return ResponseEntity.ok(despesas
                .stream()
                .map(DespesaResponse::new)
                .sorted(Comparator.comparing(DespesaResponse::data).reversed())
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaResponse> findById(@PathVariable Long id) {
        Despesa despesa = service.findById(id);
        return ResponseEntity.ok(new DespesaResponse(despesa));
    }

    @GetMapping("{ano}/{mes}")
    ResponseEntity<List<DespesaResponse>> findAllByDate(@PathVariable Integer ano, @PathVariable Integer mes) {
        List<Despesa> despesas = service.findAllByDate(ano, mes);

        return ResponseEntity.ok(despesas
                .stream()
                .map(DespesaResponse::new)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponse> put(@PathVariable Long id, @RequestBody @Valid DespesaRequest request) {
        Despesa despesa = request.toModel();
        despesa.setId(id);
        despesa = service.update(despesa);
        return ResponseEntity.ok(new DespesaResponse(despesa));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
