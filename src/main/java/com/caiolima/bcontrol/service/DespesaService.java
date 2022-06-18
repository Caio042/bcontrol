package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.exception.UnauthorizedException;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Despesa;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository repository;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    @Autowired
    public DespesaService(DespesaRepository repository, UsuarioService usuarioService, CategoriaService categoriaService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public Despesa save(Despesa despesa) {
        Usuario usuario = usuarioService.findByUsername();
        Categoria categoria = categoriaService.findById(despesa.getCategoria().getId());
        despesa.setCategoria(categoria);
        despesa.setUsuario(usuario);
        if(isDuplicateInMonth(despesa, usuario.getUsername())) {
            throw new RegistroDuplicadoException();
        }
        return repository.save(despesa);
    }

    public Despesa findById(Long id) {
        Despesa despesa = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!usuarioService.currentPrincipal().equals(despesa.getUsuario().getUsername())) {
            throw new UnauthorizedException();
        }
        return despesa;
    }

    public List<Despesa> listAll(String descricao, Long categoriaId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findAllFiltering(descricao, usuarioService.currentPrincipal(), categoriaId, dataInicio, dataFim);
    }

    @Transactional
    public Despesa update(Despesa despesa) {
        if (isDuplicateInMonth(despesa, usuarioService.currentPrincipal())) {
            throw new RegistroDuplicadoException();
        }
        Despesa despesaDB = findById(despesa.getId());
        Categoria categoria = categoriaService.findById(despesa.getCategoria().getId());
        despesa.setCategoria(categoria);
        despesa.setUsuario(despesaDB.getUsuario());
        return repository.save(despesa);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    private boolean isDuplicateInMonth(Despesa despesa, String username) {
        LocalDate dataStart = despesa.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataEnd = despesa.getData().with(TemporalAdjusters.lastDayOfMonth());
        return repository.isDuplicate(despesa.getDescricao(), dataStart, dataEnd, despesa.getId(), username);
    }

    public List<Despesa> findAllByDate(Integer ano, Integer mes) {
        return repository.findAllByDate(ano, mes, usuarioService.currentPrincipal());
    }
}
