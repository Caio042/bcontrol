package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query(value = "select (count(r) > 0) from Receita r where r.descricao = :descricao and r.data between :dataStart and :dataEnd and (:id is null or r.id <> :id)")
    boolean isDuplicate(@Param("descricao") String descricao, @Param("dataStart") LocalDate dataStart, @Param("dataEnd") LocalDate dataEnd, @Param("id") Long id);

    @Query(value = "SELECT r from Receita r where (:descricao is null or r.descricao LIKE %:descricao%)")
    List<Receita> findAllByDescricao(@Param("descricao") String descricao);

}
