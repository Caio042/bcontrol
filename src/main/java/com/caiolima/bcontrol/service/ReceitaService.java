package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.RegistroDuplicadoException;
import com.caiolima.bcontrol.exception.UnauthorizedException;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Receita;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    @Autowired
    public ReceitaService(ReceitaRepository repository, UsuarioService usuarioService, CategoriaService categoriaService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @Transactional
    public Receita save(Receita receita) {
        Usuario usuario = usuarioService.findByUsername();
        Categoria categoria = categoriaService.findById(receita.getCategoria().getId());
        receita.setCategoria(categoria);
        receita.setUsuario(usuario);

        return repository.save(receita);
    }

    public List<Receita> listAll(String descricao, Set<Long> categoriaIds, Integer ano, Integer mes) {
        String username = usuarioService.currentPrincipal();
        return repository.findAllFiltering(descricao, username, categoriaIds, ano, mes);
    }

    public Receita findById(Long id) {
        String username = usuarioService.currentPrincipal();
        Receita receita = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        if (!username.equals(receita.getUsuario().getUsername())) {
            throw new UnauthorizedException();
        }
        return receita;
    }

    @Transactional
    public Receita update(Receita receita) {

        Receita receitaDB = findById(receita.getId());
        Categoria categoria = categoriaService.findById(receita.getCategoria().getId());
        receita.setCategoria(categoria);
        receita.setUsuario(receitaDB.getUsuario());
        return repository.save(receita);
    }

    private boolean isDuplicateInMonth(Receita receita, String username) {
        LocalDate dataStart = receita.getData().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate dataEnd = receita.getData().with(TemporalAdjusters.lastDayOfMonth());
        return repository.isDuplicate(receita.getDescricao(), dataStart, dataEnd, receita.getId(), username);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public List<Receita> findAllByDate(Integer ano, Integer mes) {
        String username = usuarioService.currentPrincipal();
        return repository.findAllByDate(ano, mes, username);
    }

    public BigDecimal getValorNoMes(Integer ano, Integer mes) {
        String username = usuarioService.currentPrincipal();
        return repository.getValorNoMes(ano, mes, username)
                .orElse(BigDecimal.ZERO);
    }
}
