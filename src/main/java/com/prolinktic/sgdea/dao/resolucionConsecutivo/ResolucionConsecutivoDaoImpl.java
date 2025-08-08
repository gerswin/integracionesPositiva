package com.prolinktic.sgdea.dao.resolucionConsecutivo;

import com.prolinktic.sgdea.model.resolucion.ResolucionConsecutivo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ResolucionConsecutivoDaoImpl implements ResolucionConsecutivoDao {
    @Autowired
    ResolucionConsecutivoJpaSpring resoJpa;

    @Override
    public List<ResolucionConsecutivo> buscarConsecutivo() {
        return resoJpa.findAll();
    }

    @Override
    public void updateResolu(ResolucionConsecutivo resol) {
        resoJpa.save(resol);
    }

    @Override
    public Optional<ResolucionConsecutivo> findProximoConsecutivoDisponible(String prefijo) {
        return resoJpa.findProximoConsecutivoDisponible(prefijo);
    }

    @Transactional
    @Override
    public Optional<Long> obtenerProximoConsecutivo(String prefijo) {
        log.info("Obteniendo próximo consecutivo disponible para prefijo: {}", prefijo);

        Optional<ResolucionConsecutivo> resolucionOpt =
                resoJpa.findProximoConsecutivoDisponible(prefijo);

        if (resolucionOpt.isPresent()) {
            ResolucionConsecutivo resolucion = resolucionOpt.get();
            Long proximoConsecutivo = resolucion.getConsecutivo() + 1;


            resolucion.setConsecutivo(proximoConsecutivo);
            resoJpa.save(resolucion);

            log.info("Próximo consecutivo asignado: {} para prefijo: {}", proximoConsecutivo, prefijo);
            return Optional.of(proximoConsecutivo);
        }


        log.warn("No se encontró consecutivo disponible para prefijo: {}", prefijo);
        return Optional.empty();
    }

    @Override
    public Optional<ResolucionConsecutivo> obtenerPorPrefijo(String prefijo) {
        return resoJpa.findByRfaPrefijo(prefijo);
    }
}
