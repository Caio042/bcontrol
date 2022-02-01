package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query(value = "select (count(d) > 0) from Despesa d where d.descricao = :descricao and d.data between :dataStart and :dataEnd and (:id is null or d.id <> :id)")
    boolean isDuplicate(@Param("descricao") String descricao, @Param("dataStart") LocalDate dataStart, @Param("dataEnd") LocalDate dataEnd, @Param("id") Long id);

    @Query(value = "SELECT d FROM Despesa d WHERE (:descricao IS NULL OR d.descricao LIKE %:descricao%)")
    List<Despesa> findAllByDescricao(@Param("descricao") String descricao);
}
