package com.prolinktic.sgdea.controller.consultarArchivo;

import com.prolinktic.sgdea.service.consultarArchivo.ConsultarArchivoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("consultar_documento/")
@Api(tags = "Consultar documento", description = "Operaciones relacionadas con la consulta de documentos")
@Slf4j
public class ConsultarArchivoController {

    @Autowired
    ConsultarArchivoService consultarArchivoService;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar documento por ID",
            notes = "Obtiene información detallada de un documento basado en su ID.")
    @ApiParam(name = "id", value = "ID del documento a consultar", required = true)

    public Object consultar_documento(@PathVariable("id") String id_documento, HttpServletResponse response) {


        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);



        try {
            log.info("Iniciando la consulta del documento con ID: {}", id_documento);

            // Llamada al servicio para consultar el documento
            Object response2 = consultarArchivoService.consultarDocumento(id_documento);

            log.info("Consulta del documento con ID {} realizada con éxito. Respuesta: {}", id_documento, response2);

            return response2;
        } catch (Exception e) {
            log.error("Error al consultar el documento con ID: {}", id_documento, e);
            // Puedes manejar el error y devolver una respuesta específica si es necesario
            return "Error al consultar el documento";
        }
    }
}
