package com.caiolima.bcontrol.repository;

import com.caiolima.bcontrol.model.RegistroFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroFinanceiroRepository extends JpaRepository<RegistroFinanceiro, Long> {
    @Query(value = "SELECT DISTINCT " +
            "new java.lang.Integer(YEAR(r.data)) " +
            "FROM RegistroFinanceiro r " +
            "WHERE r.usuario.email = :email")
    List<Integer> getYears(@Param("email") String email);
}
