package com.prolinktic.sgdea.dao.SeccionSubSeccion;

import com.prolinktic.sgdea.dtos.instrumento.InstrumentoResponse;
import com.prolinktic.sgdea.dtos.instrumento.TipoDocumentoDTOEx;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeccionSubSeccionJpaSpring extends JpaRepository<SeccionSubSeccion, Integer>, JpaSpecificationExecutor<SeccionSubSeccion> {

    @Query(value = "SELECT s FROM SeccionSubSeccion s WHERE (:nombreParam IS NULL OR s.nombre LIKE %:nombreParam%) AND (:codigoParam IS NULL OR s.codigo LIKE %:codigoParam%)")
    Optional<SeccionSubSeccion> findByCod(@Param("nombreParam") String nombreParam, @Param("codigoParam") String codigoParam);

    @Query(value = "SELECT s FROM SeccionSubSeccion s JOIN CCD c ON s.idSeccionSubSeccion=c.idDependencia.idSeccionSubSeccion")
    List<SeccionSubSeccion> findSeccionSubSeccions();

    @Query(value = "SELECT s FROM SeccionSubSeccion s WHERE s.padre.idSeccionSubSeccion = :idPadre")
    List<SeccionSubSeccion> findByIdPadre(@Param("idPadre") int idPadre);

    @Query(value = " SELECT s.codigo AS codigoseccionsubseccion, s.nombre AS nombreseccionsubseccion, ss.idseriesubserie AS idseriesubserie, ss.descripcion AS nombresubserie, ss.idPadre AS idseriepadre, td.idtipologiadocumental AS idtipologiadocumental,td.nombre AS tiponombre "+
            "FROM seccionsubseccion s "+
            "JOIN seriesubserie ss ON s.idseccionsubseccion = ss.idseccionsubseccion "+
            "JOIN documentoserie ds ON ss.idseriesubserie = ds.idseriesubserie "+
            "JOIN tipologiadocumental td ON ds.idtipologiadocumental = td.idtipologiadocumental "+
            "WHERE  (:codigoSecSubsec IS NULL OR s.codigo LIKE CONCAT(:codigoSecSubsec, '%')) AND (:codigoSerieSubserie IS NULL OR ss.codigo LIKE CONCAT(:codigoSerieSubserie, '%'))"
            , nativeQuery = true)
    List<Object> findPadre(
            @Param("codigoSecSubsec") String codigoSecSubsec,
            @Param("codigoSerieSubserie") String codigoSerieSubserie
    );


    @Query("SELECT DISTINCT new com.prolinktic.sgdea.dtos.instrumento.TipoDocumentoDTOEx(td.id_tipo_documental, td.nombre) " +
            "FROM SeccionSubSeccion ss " +
            "INNER JOIN AsociacionDependencia ad on ss.idSeccionSubSeccion=ad.dependencia.idSeccionSubSeccion " +
            "JOIN CCD c on ad.idCcd.idCcd=c.idCcd " +
            "JOIN AsociacionTipoDocumental at on at.ccd.idCcd=c.idCcd " +
            "JOIN TipoDocumento td on td.id_tipo_documental =at.tipoDocumentoAsignacion.idTipoDoc " +
            "WHERE (:nombreParam IS NULL OR ss.nombre LIKE %:nombreParam%) AND (:codigoParam IS NULL OR ss.codigo LIKE %:codigoParam%)")
    List<TipoDocumentoDTOEx> findTipoDocumento(@Param("nombreParam") String nombreParam, @Param("codigoParam") String codigoParam);

}
