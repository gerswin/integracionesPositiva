package com.prolinktic.sgdea.dao.Expediente;

import com.prolinktic.sgdea.model.Expediente.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ExpedientesJpaSpring extends JpaRepository<Expediente, Integer>, JpaSpecificationExecutor<Expediente> {
    @Query("SELECT e FROM Expediente e WHERE e.nodeid = :nodeid")
    Expediente findByNodeid(@Param("nodeid") String nodeid);
}
