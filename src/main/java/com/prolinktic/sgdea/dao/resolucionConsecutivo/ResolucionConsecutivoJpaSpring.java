package com.prolinktic.sgdea.dao.resolucionConsecutivo;

import com.prolinktic.sgdea.model.resolucion.ResolucionConsecutivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResolucionConsecutivoJpaSpring extends JpaRepository<ResolucionConsecutivo,Integer>{

    Optional<ResolucionConsecutivo> findByRfaPrefijo(String rfaPrefijo);


    @Query("SELECT rc FROM ResolucionConsecutivo rc WHERE rc.rfaPrefijo = :prefijo AND rc.consecutivo < rc.consecutivoFin ORDER BY rc.consecutivo ASC")
    Optional<ResolucionConsecutivo> findProximoConsecutivoDisponible(@Param("prefijo") String prefijo);
}
