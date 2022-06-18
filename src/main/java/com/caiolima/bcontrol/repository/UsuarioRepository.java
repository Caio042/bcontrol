package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query(value = "SELECT DISTINCT " +
            "new java.lang.Integer(YEAR(r.data)) " +
            "FROM Receita r " +
            "WHERE r.usuario.email = :email " +
            "UNION " +
            "SELECT new java.lang.Integer(YEAR(d.data)) " +
            "FROM Despesa d " +
            "WHERE  d.usuario.email")
    List<Integer> getYears(@Param("email") String email);
}
