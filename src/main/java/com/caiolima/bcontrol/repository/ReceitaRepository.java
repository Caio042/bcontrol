package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query(value = "select (count(r) > 0) from Receita r where r.descricao = :descricao and r.data between :dataStart and :dataEnd and (:idToIgnore is null or r.id <> :idToIgnore) AND r.usuario.email = :email")
    boolean isDuplicate(@Param("descricao") String descricao, @Param("dataStart") LocalDate dataStart, @Param("dataEnd") LocalDate dataEnd, @Param("idToIgnore") Long idToIgnore, @Param("email") String email);

    @Query(value = "SELECT r " +
            "FROM Receita r " +
            "WHERE (:descricao IS NULL OR r.descricao LIKE %:descricao%) " +
            "AND (:ano IS NULL OR YEAR(r.data) = :ano) " +
            "AND (:mes IS NULL OR MONTH(r.data) = :mes) " +
            "AND r.categoria.id IN :categoriaIds " +
            "AND r.usuario.email = :email")
    List<Receita> findAllFiltering(@Param("descricao") String descricao,
                                   @Param("email") String email,
                                   @Param("categoriaIds") Set<Long> categoriaIds,
                                   @Param("ano") Integer ano,
                                   @Param("mes") Integer mes);

    @Query(value = "SELECT r from Receita r WHERE YEAR(r.data) = :ano AND MONTH(r.data) = :mes AND r.usuario.email = :email")
    List<Receita> findAllByDate(@Param("ano") Integer ano, @Param("mes") Integer mes, @Param("email") String email);

    @Query(value = "SELECT SUM (r.valor) FROM Receita r WHERE YEAR(r.data) = :ano AND MONTH(r.data) = :mes AND r.usuario.email = :email")
    Optional<BigDecimal> getValorNoMes(@Param("ano")Integer ano, @Param("mes") Integer mes, @Param("email") String email);

    void deleteByUsuario_Email(String email);
    void deleteByCategoria_id(Long categoriaId);
}
