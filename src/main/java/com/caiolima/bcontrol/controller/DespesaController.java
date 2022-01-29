package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.DespesaRequest;
import com.caiolima.bcontrol.controller.dto.DespesaResponse;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<DespesaResponse> save(@RequestBody @Valid DespesaRequest request, UriComponentsBuilder uriBuilder) {
        Despesa despesa = request.toModel();
        despesa = service.save(despesa);

        URI uri = uriBuilder.path("/despesas/{id}")
                .buildAndExpand(despesa.getId()).toUri();
        return ResponseEntity.created(uri)
                .body(new DespesaResponse(despesa));
    }

    @GetMapping
    public ResponseEntity<List<DespesaResponse>> listAll() {
        List<Despesa> despesas = service.listAll();
        return ResponseEntity.ok(despesas
                .stream()
                .map(DespesaResponse::new)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaResponse> findById(@PathVariable Long id) {
        Despesa despesa = service.findById(id);
        return ResponseEntity.ok(new DespesaResponse(despesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponse> put(@PathVariable Long id, @RequestBody @Valid DespesaRequest request) {
        Despesa despesa = request.toModel();
        despesa.setId(id);
        despesa = service.put(despesa);
        return ResponseEntity.ok(new DespesaResponse(despesa));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
