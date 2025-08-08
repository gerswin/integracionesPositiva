package com.prolinktic.sgdea.dao.clasificacionDocumento;

import com.prolinktic.sgdea.dtos.OpenEtlDto.ClasificacionDocumento;
import com.prolinktic.sgdea.model.clasificacionDocumento.DocumentoClasificado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClasificacionDocumentoDaoImpl implements ClasificacionDocumentoDao {

    @Autowired
    private ClasificacionDocumentoJpaSpring jpaSpring;


    @Override
    public DocumentoClasificado create(DocumentoClasificado c) {
        return jpaSpring.save(c);
    }

    @Override
    public Boolean existePorPrefijoYClasificado(String rfaPrefijo, String consecutivo) {
        return jpaSpring.existsByRfaPrefijoAndClasificacion(rfaPrefijo, consecutivo);
    }

    @Override
    public DocumentoClasificado findByRfaPrefijoAndCdoConsecutivo(String rfaPrefijo, String consecutivo) {
        return jpaSpring.findFirstByRfaPrefijoAndCdoConsecutivo(rfaPrefijo, consecutivo);
    }
}
