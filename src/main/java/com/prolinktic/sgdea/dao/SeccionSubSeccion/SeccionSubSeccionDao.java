package com.prolinktic.sgdea.dao.SeccionSubSeccion;

import com.prolinktic.sgdea.dtos.instrumento.InstrumentoResponse;
import com.prolinktic.sgdea.dtos.instrumento.TipoDocumentoDTOEx;
import com.prolinktic.sgdea.dtos.seccionsubseccion.RequestSeccion;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SeccionSubSeccionDao {
    SeccionSubSeccion recuperarSeccionSubSeccion(int id);
    List<SeccionSubSeccion> recuperarSeccionSubSeccionPadre(int idPadre);
    Optional<SeccionSubSeccion>recuperarSeccionSubSeccionCod(String des, String cod);
    void agregarSeccionSubSeccion(SeccionSubSeccion seccionSubSeccion);
    List<SeccionSubSeccion> devolverSeccionSubSeccion();
    void actualizarSeccionSubSeccion(SeccionSubSeccion seccionSubSeccion);
    void eliminarSeccionSubSeccion(int id);

    Integer agregarSeccionSubSeccionVlId(SeccionSubSeccion seccionSubSeccion);
    Page<SeccionSubSeccion> findAllPaged(Pageable pageable, RequestSeccion filtro);

    List<SeccionSubSeccion> devolverSeccionSubSeccionCCD();

    List<Object> devolverPadreIns(String codigo, String codigoSerieSubserie);
    List<TipoDocumentoDTOEx> devolverTipoDIns(String codigo, String nombre);

    List<SeccionSubSeccion> actualizacionMasiva(List<SeccionSubSeccion> listado);

}
