package com.prolinktic.sgdea.service.openDocSoporte;

import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocSoporteOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;

public interface DocumentoSoporteService {
    ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocSoporteOpen documento) throws Exception;

}

