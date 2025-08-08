package com.prolinktic.sgdea.dao.valorSecuencia;


import com.prolinktic.sgdea.model.secuencia.ValorSecuencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorSecuenciaJpaRepository extends JpaRepository<ValorSecuencia, Integer> {
}
