package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.UnauthorizedException;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.CategoriaRepository;
import com.caiolima.bcontrol.repository.DespesaRepository;
import com.caiolima.bcontrol.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final UsuarioService usuarioService;
    private final DespesaRepository despesaRepository;
    private final ReceitaRepository receitaRepository;

    public CategoriaService(CategoriaRepository repository, UsuarioService usuarioService, DespesaRepository despesaRepository, ReceitaRepository receitaRepository) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.despesaRepository = despesaRepository;
        this.receitaRepository = receitaRepository;
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        Usuario usuario = usuarioService.findByUsername();
        categoria.setUsuario(usuario);
        return repository.save(categoria);
    }

    public List<Categoria> listAll(Tipo tipo) {
        return repository.findAllByTipo(tipo, usuarioService.currentPrincipal());
    }

    @Transactional
    public Categoria update(Categoria categoria) {
        Categoria categoriaDB = findById(categoria.getId());

        categoria.setUsuario(categoriaDB.getUsuario());
        return repository.save(categoria);
    }

    public Categoria findById(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(NotFoundException::new);

        if (!categoria.getUsuario().getUsername().equals(usuarioService.currentPrincipal())) {
            throw new UnauthorizedException();
        }
        return categoria;
    }

    @Transactional
    public void deleteById(Long id) {
        Categoria categoria = findById(id);
        receitaRepository.deleteByCategoria_id(id);
        despesaRepository.deleteByCategoria_id(id);
        repository.delete(categoria);
    }
}
