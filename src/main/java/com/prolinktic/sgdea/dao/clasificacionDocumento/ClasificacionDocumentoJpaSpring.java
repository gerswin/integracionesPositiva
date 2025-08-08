package com.prolinktic.sgdea.dao.clasificacionDocumento;


import com.prolinktic.sgdea.model.clasificacionDocumento.DocumentoClasificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClasificacionDocumentoJpaSpring extends JpaRepository<DocumentoClasificado, Integer>, JpaSpecificationExecutor<DocumentoClasificado> {

    @Query("SELECT CASE WHEN COUNT(dc) > 0 THEN TRUE ELSE FALSE END " +
           "FROM DocumentoClasificado dc " +
           "WHERE dc.rfaPrefijo = :rfaPrefijo AND dc.cdoConsecutivo = :cdo_consecutivo")
    boolean existsByRfaPrefijoAndClasificacion(@Param("rfaPrefijo") String rfaPrefijo,
                                               @Param("cdo_consecutivo") String cdoConsecutivo);


    @Query("SELECT dc FROM DocumentoClasificado dc " +
           "WHERE dc.rfaPrefijo = :rfaPrefijo AND dc.cdoConsecutivo = :cdo_consecutivo")
    DocumentoClasificado findByRfaPrefijoAndCdoConsecutivo(@Param("rfaPrefijo") String rfaPrefijo,
                                                           @Param("cdo_consecutivo") String cdoConsecutivo);

    @Query(value = "SELECT * FROM documento_clasificado WHERE rfa_prefijo = :rfaPrefijo AND cdo_consecutivo = :cdo_consecutivo LIMIT 1", nativeQuery = true)
    DocumentoClasificado findFirstByRfaPrefijoAndCdoConsecutivo(@Param("rfaPrefijo") String rfaPrefijo,
                                                                @Param("cdo_consecutivo") String cdoConsecutivo);


}
