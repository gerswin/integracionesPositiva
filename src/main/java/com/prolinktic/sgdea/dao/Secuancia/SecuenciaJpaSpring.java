package com.prolinktic.sgdea.dao.Secuancia;


import com.prolinktic.sgdea.model.secuencia.Secuencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecuenciaJpaSpring extends JpaRepository<Secuencia, Long> {
    Secuencia findByNombreCorto(String nombreCorto);
}
