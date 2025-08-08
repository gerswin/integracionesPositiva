package com.prolinktic.sgdea.service.facturacionDescargaOpen;


import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.exception.FacturacionException;
import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface FacturacionDescargaOpen {

    ResponseFacDes descargarFacturacion(String cufe, String fecha);

    ResponseListFacDes traerOpen(String fechaInicio, String fechaFin);

    ResposneMarca marcarFacturacion(String cufe);

    List<FacturacionDescarga> listarFacturacion(String fecha, String prefijo, String consecutivo, String proveedor);

    ResponseConsultaDocumentoDto consultaDocumento(Integer ofeIdentificacion, String prefijo, Integer consecutivo,
                                                   String tipo, String proveedor, String cufe, String fecha) throws FacturacionException;

    ResponseClasifDocDto clasificacionDocumento(ClasificacionDocumentoRequestDto request) throws FacturacionException;

    CompletableFuture<ResponseListFacDes> traerOpenAsync(String fechaInicial, String fechaFinal,
                                                         String prefijo,
                                                         String consecutivo,
                                                         String proveedor);

    List<FacturacionDescargaDto> listarFacturasProvedor(String proveedor);
}
