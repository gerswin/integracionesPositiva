package com.prolinktic.sgdea.service.consultarArchivo;

public interface ConsultarArchivoService {
    Object consultarDocumento(String id_documento);
    String consultaArchivoContentById (String id_documento);
}
