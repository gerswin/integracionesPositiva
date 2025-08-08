package com.prolinktic.sgdea.controller.Dependencias;

import com.prolinktic.sgdea.dtos.dependencias.ResponseDependencias;
import com.prolinktic.sgdea.service.dependencias.DependenciasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("listado_maestro_dependencias/")
@Api(tags = "Dependencias", description = "Operaciones relacionadas con el listado maestro de dependencias")
@RequiredArgsConstructor
@Slf4j
public class DependenciasController {

    private final DependenciasService dependenciasService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listado maestro de dependencias",
            notes = "Obtiene el listado maestro de dependencias en formato JSON.")
    public ResponseDependencias listadoMaestroDependencias(HttpServletResponse response) {


        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);


        try {
            log.info("Iniciando la obtención del listado maestro de dependencias");

            // Llamada al servicio para obtener el listado de dependencias
            ResponseDependencias response2 = dependenciasService.consultarDocumento();

            log.info("Listado maestro de dependencias obtenido con éxito. Respuesta: {}", response2);

            return response2;
        } catch (Exception e) {
            log.error("Error al obtener el listado maestro de dependencias", e);
            // Puedes manejar el error y devolver una respuesta específica si es necesario
            return new ResponseDependencias("Error al obtener el listado de dependencias", null, null, 500, null);
        }
    }
}

