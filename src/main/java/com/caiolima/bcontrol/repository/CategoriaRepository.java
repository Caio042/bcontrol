package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Categoria;
import com.caiolima.bcontrol.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("select c from Categoria c where c.tipo = :tipo and c.usuario.email = :email")
    List<Categoria> findAllByTipo(@Param("tipo") Tipo tipo, @Param("email") String email);

    void deleteByUsuario_Email(String email);
}
