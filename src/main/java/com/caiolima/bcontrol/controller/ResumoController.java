package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.response.ResumoDTO;
import com.caiolima.bcontrol.service.ResumoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Resumos", description = "Operações relacionadas a resumo do mês")
@RestController
@RequestMapping("/resumo")
public class ResumoController {

    private final ResumoService service;

    @Autowired
    public ResumoController(ResumoService service) {
        this.service = service;
    }

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<ResumoDTO> monthAbstract(@PathVariable Integer ano, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.generateResumo(ano, mes));
    }
}