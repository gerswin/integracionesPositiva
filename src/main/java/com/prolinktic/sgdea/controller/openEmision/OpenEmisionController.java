package com.prolinktic.sgdea.controller.openEmision;

import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocEmisionOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.service.openEmision.OpenEmisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/emision")
@RestController
@Api(tags = "Facturación Management API", description = "Servicios Integración Facturación OpenEtlAPI")
public class OpenEmisionController {

    @Autowired
    private OpenEmisionService openEmisionService;

    @Operation(summary = "Este método registra documentos en Open Emisión mediante FC, NC o ND",
            description = "Este método registra documentos en Open Emisión mediante FC, NC o ND",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Registro exitoso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseCreateDocOpenDTO.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @PostMapping("/registrarDocumento")
    public ResponseEntity<ResponseCreateDocOpenDTO> crearDocumentoEnEmision(
            @RequestBody CreateDocEmisionOpen documento) {
        try {
            ResponseCreateDocOpenDTO response = openEmisionService.crearDocumentoEnEmision(documento);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Este método obtiene el PDF de un documento en Open Emisión",
            description = "Este método obtiene el PDF de un documento en Open Emisión",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Obtención exitosa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PdfNotificacionDto.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @GetMapping("/obtenerPdf")
    public ResponseEntity<Object> obtenerPdfEmision(
            @RequestParam String tipoDocumento,
            @RequestParam String prefijo,
            @RequestParam Integer consecutivo,
            @RequestParam Integer ofeIdentificacion,
            @RequestParam(defaultValue = "base64") String resultado) {
        try {
            PdfNotificacionDto response = openEmisionService.obtenerPdfEmision(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
            if ("archivo".equalsIgnoreCase(resultado)) {

                byte[] pdfBytes = java.util.Base64.getDecoder().decode(response.getPdfNotificacion());
                return ResponseEntity
                        .ok()
                        .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"documento.pdf\"")
                        .body(pdfBytes);
            } else {

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Este método obtiene el XML UBL DIAN de un documento en Open Emisión",
            description = "Este método obtiene el XML UBL DIAN de un documento en Open Emisión",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Obtención exitosa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = XmlUblDianDto.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @GetMapping("/obtenerXml")
    public ResponseEntity<Object> obtenerXmlEmision(
            @RequestParam String tipoDocumento,
            @RequestParam String prefijo,
            @RequestParam Integer consecutivo,
            @RequestParam Integer ofeIdentificacion,
            @RequestParam(defaultValue = "base64") String resultado) {
        try {
            XmlUblDianDto response = openEmisionService.obtenerXmlEmision(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
            if ("archivo".equalsIgnoreCase(resultado)) {

                byte[] xmlBytes = java.util.Base64.getDecoder().decode(response.getXmlUbl());
                return ResponseEntity
                        .ok()
                        .contentType(org.springframework.http.MediaType.APPLICATION_XML)
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"documento.xml\"")
                        .body(xmlBytes);
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Este método obtiene el Attached Document de un documento en Open Emisión",
            description = "Este método obtiene el Attached Document de un documento en Open Emisión",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Obtención exitosa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @GetMapping("/obtenerAttachedDocument")
    public ResponseEntity<Object> obtenerAttachedDocument(
            @RequestParam String tipoDocumento,
            @RequestParam String prefijo,
            @RequestParam String consecutivo,
            @RequestParam Integer ofeIdentificacion,
            @RequestParam(defaultValue = "base64") String resultado) {
        try {
            String response = openEmisionService.obtenerAttachedDocument(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
            if ("archivo".equalsIgnoreCase(resultado)) {

                byte[] xmlBytes = java.util.Base64.getDecoder().decode(response);
                return ResponseEntity
                        .ok()
                        .contentType(org.springframework.http.MediaType.APPLICATION_XML)
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"attached-document.xml\"")
                        .body(xmlBytes);
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Este método consulta los datos generales de un documento en Open Emisión",
            description = "Este método consulta los datos generales de un documento en Open Emisión",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Consulta exitosa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DatosGeneralesDocDto.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @GetMapping("/consultarDatosGenerales")
    public ResponseEntity<DatosGeneralesDocDto> consultarDatosGenerales(
            @RequestParam Integer ofeIdentificacion,
            @RequestParam String prefijo,
            @RequestParam Integer consecutivo) {
        try {
            DatosGeneralesDocDto response = openEmisionService.consultarDatosGenerales(ofeIdentificacion, prefijo, consecutivo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}