package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query(value = "select (count(d) > 0) from Despesa d where d.descricao = :descricao and d.data between :dataStart and :dataEnd and (:idToIgnore is null or d.id <> :idToIgnore) and d.usuario.username = :username")
    boolean isDuplicate(@Param("descricao") String descricao, @Param("dataStart") LocalDate dataStart, @Param("dataEnd") LocalDate dataEnd, @Param("idToIgnore") Long idToIgnore, @Param("username") String username);

    @Query(value = "SELECT d FROM Despesa d WHERE (:descricao IS NULL OR d.descricao LIKE %:descricao%) AND d.usuario.username = :username")
    List<Despesa> findAllByDescricao(@Param("descricao") String descricao, @Param("username") String username);

    @Query(value = "SELECT d FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes AND d.usuario.username = :username")
    List<Despesa> findAllByDate(@Param("ano") Integer ano, @Param("mes") Integer mes, @Param("username") String username);

    void deleteByUsuario_Username(String username);
}
