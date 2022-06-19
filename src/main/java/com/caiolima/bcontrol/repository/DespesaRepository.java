package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query(value = "select (count(d) > 0) from Despesa d where d.descricao = :descricao and d.data between :dataStart and :dataEnd and (:idToIgnore is null or d.id <> :idToIgnore) and d.usuario.email = :email")
    boolean isDuplicate(@Param("descricao") String descricao, @Param("dataStart") LocalDate dataStart, @Param("dataEnd") LocalDate dataEnd, @Param("idToIgnore") Long idToIgnore, @Param("email") String email);

    @Query(value = "SELECT d " +
            "FROM Despesa d " +
            "WHERE (:descricao IS NULL OR d.descricao LIKE %:descricao%) " +
            "AND (:ano IS NULL OR YEAR(d.data) = :ano) " +
            "AND (:mes IS NULL OR MONTH(d.data) = :mes) " +
            "AND d.categoria.id IN :categoriaIds " +
            "AND d.usuario.email = :email")
    List<Despesa> findAllFiltering(@Param("descricao") String descricao,
                                   @Param("email") String email,
                                   @Param("categoriaIds") Set<Long> categoriaIds,
                                   @Param("ano") Integer ano,
                                   @Param("mes") Integer mes);

    @Query(value = "SELECT d FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes AND d.usuario.email = :email")
    List<Despesa> findAllByDate(@Param("ano") Integer ano, @Param("mes") Integer mes, @Param("email") String email);

    void deleteByUsuario_Email(String email);
    void deleteByCategoria_id(Long categoriaId);
}
