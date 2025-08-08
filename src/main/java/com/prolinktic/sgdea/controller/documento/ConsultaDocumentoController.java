package com.prolinktic.sgdea.controller.documento;


import com.prolinktic.sgdea.dtos.buscarDocumento.ResponseBuscarDocumento;
import com.prolinktic.sgdea.service.consultarDocumento.DocumentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("buscar_documento_radicado/")
@Api(tags = " Buscar documento radicado")
@RequiredArgsConstructor
@Slf4j
public class ConsultaDocumentoController {

    private final DocumentoService documentoService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar documento",
            notes = "Buscar documentos por id de radicado ")
    @ApiParam(name = "id", value = "ID del radicado a consultar", required = true)
    public Object buscarDocumento(@PathVariable("id") String idDocumento, HttpServletResponse response) throws Exception {

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);


        log.info("Inicio buscar documento por idRadicado {}", idDocumento);

        ResponseBuscarDocumento response2 = documentoService.buscarDocumento(idDocumento);

        log.info("Respuesta buscar documento por idRadicado Respuesta: {}", response2);

        return response2;
    }
}

