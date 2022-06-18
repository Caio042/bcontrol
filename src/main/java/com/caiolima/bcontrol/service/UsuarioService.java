package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.DuplicateUsernameException;
import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.CategoriaRepository;
import com.caiolima.bcontrol.repository.DespesaRepository;
import com.caiolima.bcontrol.repository.ReceitaRepository;
import com.caiolima.bcontrol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, ReceitaRepository receitaRepository, DespesaRepository despesaRepository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        if (repository.existsByEmail(usuario.getUsername())) {
            throw new DuplicateUsernameException();
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario = repository.save(usuario);

        Categoria categoriaDespesa = new Categoria("Outros", Tipo.DESPESA, "#000");
        categoriaDespesa.setUsuario(usuario);
        Categoria categoriaReceita = new Categoria("Outros", Tipo.RECEITA, "#000");
        categoriaReceita.setUsuario(usuario);
        categoriaRepository.saveAll(List.of(categoriaReceita, categoriaDespesa));
        return usuario;
    }

    public Usuario findByUsername(String userName) {
        return repository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe"));
    }

    public Usuario findByUsername() {
        return repository.findByEmail(currentPrincipal())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    @Transactional
    public Usuario update(Usuario usuario) {
        Usuario usuarioDB = findByUsername(usuario.getUsername());
        usuario.setId(usuarioDB.getId());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    @Transactional
    public void delete(String username) {
        categoriaRepository.deleteByUsuario_Email(username);
        receitaRepository.deleteByUsuario_Email(username);
        despesaRepository.deleteByUsuario_Email(username);
        repository.deleteByEmail(username);
    }

    public Usuario currentUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String currentPrincipal() {
        return currentUser().getUsername();
    }


    public List<Integer> getYearAvailable() {
        return repository.getYears(currentPrincipal());
    }
}
