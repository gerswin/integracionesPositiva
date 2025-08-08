package com.prolinktic.sgdea.controller.documentoSoporte;


import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocSoporteOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.service.openDocSoporte.DocumentoSoporteService;
import io.swagger.annotations.Api;
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
@RequestMapping(value = "/doc_soporte")
@RestController
@Api(tags = "Facturación Management API", description = "Servicios Integración Facturación OpenEtlAPI")
public class DocumentoSoporteController {

    @Autowired
    private DocumentoSoporteService documentoSoporteService;

    @Operation(summary = "Este método registra documentos en Open  mediante DS o DS_NC",
            description = "Este método registra documentos en Open  mediante DS o DS_NC",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Registro exitoso",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseCreateDocOpenDTO.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content)
            })
    @PostMapping("/registrar")
    public ResponseEntity<ResponseCreateDocOpenDTO> crearDocumentoEnEmision(
            @RequestBody CreateDocSoporteOpen documento) {
        try {
            ResponseCreateDocOpenDTO response = documentoSoporteService.crearDocumentoEnEmision(documento);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}