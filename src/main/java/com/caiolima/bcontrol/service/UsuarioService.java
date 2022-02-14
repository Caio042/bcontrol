package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.DuplicateUsername;
import com.caiolima.bcontrol.exception.NotFoundException;
import com.caiolima.bcontrol.model.Usuario;
import com.caiolima.bcontrol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        if (repository.existsByUsername(usuario.getUsername())) {
            throw new DuplicateUsername();
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public Usuario findByUserName(String userName) {
        return repository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não existe"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUserName(username);
    }

    @Transactional
    public Usuario atualize(Usuario usuario) {
        Usuario usuarioDB = findByUserName(usuario.getUsername());
        usuario.setId(usuarioDB.getId());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }
}
