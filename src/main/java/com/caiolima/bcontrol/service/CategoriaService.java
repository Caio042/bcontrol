package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.exception.UnauthorizedException;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final UsuarioService usuarioService;

    public CategoriaService(CategoriaRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        Usuario usuario = usuarioService.currentUser();
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

    private Categoria findById(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(NotFoundException::new);

        if (!categoria.getUsuario().getUsername().equals(usuarioService.currentPrincipal())) {
            throw new UnauthorizedException();
        }
        return categoria;
    }

    @Transactional
    public void deleteById(Long id) {
        Categoria categoria = findById(id);
        repository.delete(categoria);
    }
}
