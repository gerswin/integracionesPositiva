package com.prolinktic.sgdea.service.openEmision;

import com.prolinktic.sgdea.dtos.OpenEtlDto.DatosGeneralesDocDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.PdfNotificacionDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.XmlUblDianDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocEmisionOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;

public interface OpenEmisionService {
    ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocEmisionOpen documento) throws Exception;

    PdfNotificacionDto obtenerPdfEmision(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) throws Exception;

    XmlUblDianDto obtenerXmlEmision(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) throws Exception;

    String obtenerAttachedDocument(String tipoDocumento, String prefijo, String consecutivo, Integer ofeIdentificacion) throws Exception;

    DatosGeneralesDocDto consultarDatosGenerales(Integer ofeIdentificacion, String prefijo, Integer consecutivo) throws Exception;
}

