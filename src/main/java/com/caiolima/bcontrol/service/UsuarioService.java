package com.caiolima.bcontrol.service;

import com.caiolima.bcontrol.exception.DuplicateUsernameException;
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
            throw new DuplicateUsernameException();
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public Usuario findByUsername(String userName) {
        return repository.findByUsername(userName)
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
        repository.deleteByUsername(username);
    }
}
