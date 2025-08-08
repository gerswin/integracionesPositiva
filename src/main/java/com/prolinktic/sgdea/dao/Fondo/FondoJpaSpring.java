package com.prolinktic.sgdea.dao.Fondo;

import com.prolinktic.sgdea.model.Fondo.Fondo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FondoJpaSpring extends JpaRepository<Fondo, Integer>, JpaSpecificationExecutor<Fondo> {

}
