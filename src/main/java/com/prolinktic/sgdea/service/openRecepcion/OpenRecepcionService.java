package com.prolinktic.sgdea.service.openRecepcion;


import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.FiltroProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.ProveedorResponseDTO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface OpenRecepcionService {

    ResponseRegistroOpen registrarDocumentoFileRecepcion(Integer ofeIdentificacion, MultipartFile pdf, MultipartFile xml, String cufe) throws Exception;

    ResponseRegistroOpen registrarDocumentoJsonRecepcion(Integer ofeIdentificacion, List<DocumentoDTO> documentos) throws Exception;

    ResponseRegistroEventoOpen registrarEvento(String evento, List<DocumentoEventoDTO> documentos);

    ProveedorResponseDTO listarProveedores(FiltroProveedorDTO request) throws Exception;

    CrearProveedorResponseDTO crearProveedor(CrearProveedorDTO request) throws Exception;
}
