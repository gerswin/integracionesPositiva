package com.prolinktic.sgdea.service.instrumento;

import com.prolinktic.sgdea.dao.SeccionSubSeccion.SeccionSubSeccionDao;
import com.prolinktic.sgdea.dao.Serie.SerieDao;
import com.prolinktic.sgdea.dtos.instrumento.*;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class InstrumentoServiceImpl implements InstrumentoService {
    @Autowired
    SeccionSubSeccionDao seccionSubSeccionDao;

    @Autowired
    SerieDao serieDao;

    @Override
    public Map<String, Object> consultarInstrumentos(String codigo, String codigoSerieSubserie) {
        List<Object> instrumentos = seccionSubSeccionDao.devolverPadreIns(codigo, codigoSerieSubserie);
        Map<String, Map<String, Object>> resultadosMap = new HashMap<>();

        for (Object fila : instrumentos) {
            Object[] filaArray = (Object[]) fila;

            String codigoseccionsubseccion = String.valueOf(filaArray[0]);
            String nombreseccionsubseccion = String.valueOf(filaArray[1]);
            String idseriepadre = (filaArray[4] != null) ? String.valueOf(filaArray[4]) : null;

            if (!resultadosMap.containsKey(codigoseccionsubseccion)) {
                resultadosMap.put(codigoseccionsubseccion, new HashMap<>());
//                resultadosMap.get(codigoseccionsubseccion).put("codigo_serie", codigoseccionsubseccion);
//                resultadosMap.get(codigoseccionsubseccion).put("nombre_serie", nombreseccionsubseccion);
                resultadosMap.get(codigoseccionsubseccion).put("subserie", new HashSet<>()); // Usar un HashSet para evitar duplicados
                resultadosMap.get(codigoseccionsubseccion).put("serie", new HashSet<>()); // Usar un HashSet para evitar duplicados
                resultadosMap.get(codigoseccionsubseccion).put("oficina", new HashSet<>()); // Usar un HashSet para evitar duplicados
                resultadosMap.get(codigoseccionsubseccion).put("tipo", new HashSet<>()); // Usar un HashSet para evitar duplicados
            }

            // Verificar si idseriepadre es diferente de null
            if (idseriepadre != null) {
                // Agregar la serie al conjunto de series
                Map<String, Object> serieMap = new HashMap<>();
                serieMap.put("codigo_serie", Integer.valueOf(String.valueOf(filaArray[2])));
                serieMap.put("nombre_serie", String.valueOf(filaArray[3]));
                ((Set<Map<String, Object>>) resultadosMap.get(codigoseccionsubseccion).get("serie")).add(serieMap);
            } else {
                // Agregar los valores en la lista de subserie
                Map<String, Object> subserieMap = new HashMap<>();
                subserieMap.put("codigo_subserie", Integer.valueOf(String.valueOf(filaArray[2])));
                subserieMap.put("nombre_subserie", String.valueOf(filaArray[3]));
                ((Set<Map<String, Object>>) resultadosMap.get(codigoseccionsubseccion).get("subserie")).add(subserieMap);
            }

            Map<String, Object> oficinaMap = new HashMap<>();
            oficinaMap.put("codigo_oficina", codigoseccionsubseccion);
            oficinaMap.put("nombre_oficina", nombreseccionsubseccion);
            ((Set<Map<String, Object>>) resultadosMap.get(codigoseccionsubseccion).get("oficina")).add(oficinaMap);

            // Agregar el tipo al conjunto de tipos
            Map<String, Object> tipoMap = new HashMap<>();
            tipoMap.put("idTipoDoc", Integer.valueOf(String.valueOf(filaArray[5])));
            tipoMap.put("nombre", String.valueOf(filaArray[6]));
            ((Set<Map<String, Object>>) resultadosMap.get(codigoseccionsubseccion).get("tipo")).add(tipoMap);
        }

        // Crear el mapa final con los campos adicionales
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("body", resultadosMap.values());
        resultMap.put("message", "OK");
        resultMap.put("fecha_recepcion", LocalDateTime.now().toString());
        resultMap.put("id_transaccion", UUID.randomUUID());

        return resultMap;
    }



}
