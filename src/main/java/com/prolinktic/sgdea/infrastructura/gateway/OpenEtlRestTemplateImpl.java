package com.prolinktic.sgdea.infrastructura.gateway;

import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.FiltroProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.ProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocEmisionOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocSoporteOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.FiltroResolucionFacturacionDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.ResolucionResponseDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface OpenEtlRestTemplateImpl {
    Optional<ConsultaDocOpen> descargarFacturacion(String cufe, String fecha);

    ResponseDataList listarFacturacion(String fechaInicio, String fechaFin);

    void marcarFacturacion();

    XmlUblDianDto obtieneXmlUblDIAN(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion);

    PdfNotificacionDto obtenerPdf(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion);

    DatosGeneralesDocDto consultarDatosGenerales(Integer ofeIdentificacion, String prefijo, Integer consecutivo);

    ResponseRegistroEventoOpen clasificacionDocumento(ClasificacionDocumentoRequestDto resp, String tdeCodigo, String observacion, String creCodigo);

    List<ConsultaDocDto> consultarDocumentoRecepcion(Integer ofeIdentificacion, String prefijo, Integer consecutivo,
                                                     String tipo, String proveedor, String cufe, String fecha);

    ResponseRegistroOpen registrarDocumentoFileRecepcion(Integer ofeIdentificacion, File pdf, File xml, String cufe);

    ResponseRegistroOpen registrarDocumentoJsonRecepcion(Integer ofeIdentificacion, List<DocumentoDTO> documentos);

    ResponseRegistroEventoOpen registroEventoRecepcion(String request);

    ResponseRegistroOpen registroDocumento(Integer ofeIdentificacion, File pdf, File xml, String cufe);

    String obtenerAttachedDocumentDocumentoEnEmision(String tipoDoc, String prefijo, String consecutivo, Integer ofeIdentificacion);

    ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocEmisionOpen documento);

    ResponseCreateDocOpenDTO crearDocumentoSoporte(CreateDocSoporteOpen documento);

    ProveedorResponseDTO listarProveedores(FiltroProveedorDTO request);

    CrearProveedorResponseDTO crearProveedor(CrearProveedorDTO request);

    ResolucionResponseDTO listarResolucionesFacturacion(FiltroResolucionFacturacionDTO filtro);
}
