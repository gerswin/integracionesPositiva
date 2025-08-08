package com.prolinktic.sgdea.dao.clasificacionDocumento;

import com.prolinktic.sgdea.model.clasificacionDocumento.DocumentoClasificado;


public interface ClasificacionDocumentoDao {

    DocumentoClasificado create(DocumentoClasificado c);

    Boolean existePorPrefijoYClasificado(String rfaPrefijo, String consecutivo);


    DocumentoClasificado findByRfaPrefijoAndCdoConsecutivo(String rfaPrefijo, String consecutivo);
}
