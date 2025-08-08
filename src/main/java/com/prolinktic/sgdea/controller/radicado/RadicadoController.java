package com.prolinktic.sgdea.controller.radicado;

import javax.servlet.http.Cookie;

import com.prolinktic.sgdea.dtos.AlfrescoNodeList;
import com.prolinktic.sgdea.dtos.radicado.AgregarAnexos;
import com.prolinktic.sgdea.dtos.radicado.AnexoResponse;
import com.prolinktic.sgdea.dtos.radicado.RadicadoEntradaDTO;
import com.prolinktic.sgdea.exception.AnexosInvalidosException;
import com.prolinktic.sgdea.exception.CarpetaNoEncontradaException;
import com.prolinktic.sgdea.exception.exceptions.ControlException;
import com.prolinktic.sgdea.service.consultarDocumento.DocumentoService;
import com.prolinktic.sgdea.service.radicado.RadicadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("radicar_documento/")
@Api(tags = "Radicar documento", description = "Operaciones relacionadas con el anexo de radicados de un expediente")
public class RadicadoController {

    @Autowired
    private RadicadoService service;

    @Autowired
    private DocumentoService documentoService;

    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Crear un nuevo radicado y adjuntar sus anexos",
            notes = "Crear un nuevo radicado y adjuntar sus anexos")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object createEntrada(HttpServletResponse response, @RequestBody MultipartFile file, @RequestBody MultipartFile[] anexos, @ModelAttribute RadicadoEntradaDTO radicadoEntradaDTO) throws Exception {

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        log.info("Solicitud para agregar anexos a un radicado nuevo. Anexos: {}, Datos: {}", anexos, radicadoEntradaDTO);
       // Object response = service.createEntrada(radicadoEntradaDTO, anexos, file);
        log.info("Respuesta de solicitud creación radicado {}", response);
        return service.createEntrada(radicadoEntradaDTO, anexos, file);
        //return response;

    }

    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Agregar anexos a un radicado existente",
            notes = "Agrega anexos a un radicado existente en el sistema.")
    @PostMapping(value = "agregarAnexos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> agregarAnexosParaRadicado(HttpServletResponse response,

                                                       @ModelAttribute AgregarAnexos agregarAnexos
    ) {
        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
       // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        try {
            log.info("Solicitud para agregar anexos a un radicado existente. Anexos: {}, Datos: {}", "anexo", agregarAnexos);

            AnexoResponse resultado = service.agregarAnexos(agregarAnexos, agregarAnexos.getAnexos().toArray(new MultipartFile[0]));
            log.info("Respuesta de la solicitud para agregar anexos: {}", resultado);

            return ResponseEntity.ok(resultado);
        } catch (CarpetaNoEncontradaException | AnexosInvalidosException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error en el servicio al procesar la solicitud");
        }
    }


    @CrossOrigin(origins = "*")
    @GetMapping(value = "obtenerHostName")
    public String obtenerHostName(@NotNull HttpServletRequest request, HttpServletResponse response) {
        // Obtiene la dirección IP del cliente
        String ipAddress = request.getRemoteAddr();

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        // Obtiene el nombre del host a partir de la dirección IP
        String hostname = null;
        try {
            java.net.InetAddress inetAddress = java.net.InetAddress.getByName(ipAddress);
           // hostname = inetAddress.getHostName();
            hostname = request.getRequestURL().toString();
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace(); // Manejar la excepción según la lógica
        }

        return hostname;
    }


    @CrossOrigin(origins = "*")
    @GetMapping(value = "obtenerHijosPorNodo/{nodeId}")
    public AlfrescoNodeList obtenerHijosPorNodo(HttpServletResponse response, @NotNull HttpServletRequest request, @PathVariable("nodeId") String nodeId) throws  ControlException {

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        return documentoService.obtenerHijosPorNodeId(nodeId);
    }


    /*
    @CrossOrigin(origins = "*")
    @GetMapping(value = "pruebas/{codigo}")
    public String pruebas(@NotNull HttpServletRequest request, @PathVariable("codigo") String codigo) throws JSONException {

        Object respuesta = service.obtenerNodeAlfrescoDeSeccionSubSeccion(codigo);

        return respuesta.toString();
    } */



}
