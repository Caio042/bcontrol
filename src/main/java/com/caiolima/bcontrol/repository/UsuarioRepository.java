package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
