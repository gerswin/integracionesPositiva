package com.prolinktic.sgdea.controller.openRecepcion;

import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.FiltroProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.ProveedorResponseDTO;
import com.prolinktic.sgdea.service.openRecepcion.OpenRecepcionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/recepcion")
@RestController
@Api(tags = "Facturación Management API", description = "Servicios Integración Facturación OpenEtlAPI")
public class OpenRecepcionController {

    @Autowired
    private OpenRecepcionService openRecepcionService;

    @Operation(summary = "Registra un archivo PDF y XML en Open Recepción",
            description = "Permite registrar un archivo PDF y XML para su procesamiento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registro exitoso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseRegistroOpen.class)))
            })
    @PostMapping(value = "/documento/registraFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseRegistroOpen> registraFile(
            @RequestParam("ofeIdentificacion") Integer ofeIdentificacion,
            @RequestPart("pdf") MultipartFile pdf,
            @RequestPart("xml") MultipartFile xml,
            @RequestParam("cufe") String cufe) {

        log.info("Iniciando el proceso de registro de documento en Open Recepción");

        try {
            ResponseRegistroOpen response = openRecepcionService.registrarDocumentoFileRecepcion(ofeIdentificacion, pdf, xml, cufe);
            log.info("Documento registrado exitosamente con CUFE: {}", cufe);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error al registrar los documentos", e);
            return ResponseEntity.badRequest().body(new ResponseRegistroOpen(
                    "Error al registrar los documentos en Open",
                    List.of(e.getMessage()),
                    null
            ));
        }
    }

    @Operation(summary = "Este método registra documentos en Open recepcion mediante JSON",
            description = "Este método recibe una lista de documentos JSON y los registra en Open recepcion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Documentos registrados correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseRegistroOpen.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos o error en el registro",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping("/documento/registraJson")
    public ResponseEntity<ResponseRegistroOpen> registrarDocumentoJsonRecepcion(
            @RequestParam Integer ofeIdentificacion,
            @RequestBody List<DocumentoDTO> documentos) {
        try {
            ResponseRegistroOpen response = openRecepcionService.registrarDocumentoJsonRecepcion(ofeIdentificacion, documentos);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al registrar los documentos", e);
            return ResponseEntity.badRequest().body(new ResponseRegistroOpen(
                    "Error al registrar los documentos en Open",
                    List.of(e.getCause().getMessage()),
                    null
            ));
        }
    }


    @Operation(summary = "Este método registra eventos en documentos de Open recepción",
            description = "Este método recibe un tipo de evento y una lista de documentos para aplicar dicho evento en Open recepción",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eventos registrados correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseRegistroEventoOpen.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos o error en el registro",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping("/evento/registrar")
    public ResponseEntity<ResponseRegistroEventoOpen> registrarEvento(@RequestBody EventoRequest request) {
        try {
            ResponseRegistroEventoOpen response = openRecepcionService.registrarEvento(
                    request.getEvento(),
                    request.getDocumentos()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseRegistroEventoOpen(
                            "Error al procesar la solicitud: " + e.getMessage(),
                            null,
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @Operation(
            summary = "Listar proveedores",
            description = "Devuelve una lista paginada de proveedores según los filtros proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de proveedores obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProveedorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al procesar la solicitud",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseRegistroEventoOpen.class))
            )
    })
    @GetMapping("/proveedores/listar")
    public ResponseEntity<?> listarProveedores(@RequestBody FiltroProveedorDTO request) {
        try {
            ProveedorResponseDTO response = openRecepcionService.listarProveedores(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseRegistroEventoOpen(
                            "Error al procesar la solicitud: " + e.getMessage(),
                            null,
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Operation(
            summary = "Crear proveedor",
            description = "Permite registrar un nuevo proveedor en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Proveedor creado exitosamente o con advertencias",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CrearProveedorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al procesar la solicitud",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseRegistroEventoOpen.class))
            )
    })
    @PostMapping("/proveedores/Crear")
    public ResponseEntity<?> crearProveedor(@RequestBody CrearProveedorDTO request) {
        try {
            CrearProveedorResponseDTO response = openRecepcionService.crearProveedor(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseRegistroEventoOpen(
                            "Error al procesar la solicitud: " + e.getMessage(),
                            null,
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}
