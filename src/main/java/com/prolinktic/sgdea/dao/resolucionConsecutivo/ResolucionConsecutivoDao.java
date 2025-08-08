package com.prolinktic.sgdea.dao.resolucionConsecutivo;


import com.prolinktic.sgdea.model.resolucion.ResolucionConsecutivo;

import java.util.List;
import java.util.Optional;

public interface ResolucionConsecutivoDao {
    List<ResolucionConsecutivo> buscarConsecutivo();

    void updateResolu(ResolucionConsecutivo resol);

    Optional<ResolucionConsecutivo> findProximoConsecutivoDisponible(String prefijo);

    Optional<Long> obtenerProximoConsecutivo(String prefijo);

    Optional<ResolucionConsecutivo> obtenerPorPrefijo(String prefijo);
}
