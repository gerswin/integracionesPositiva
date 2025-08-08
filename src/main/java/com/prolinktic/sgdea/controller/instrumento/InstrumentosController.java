package com.prolinktic.sgdea.controller.instrumento;

import com.prolinktic.sgdea.service.instrumento.InstrumentoService;
import com.prolinktic.sgdea.service.loginUsuario.LoginUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("consulta_instrumentos")
@Api(tags = "Instrumentos", description = "Operaciones relacionadas con la obtención de instrumentos")
public class InstrumentosController {

    @Autowired
    InstrumentoService instrumentoService;


    @Autowired
    LoginUsuarioService loginUsuarioService;

    @ApiOperation(value = "Obtener instrumentos",
            notes = "Obtiene una lista de instrumentos basada en el código de la oficina productora.")

    @GetMapping
    public ResponseEntity<?> obtenerInstrumentos(
            @ApiParam(value = "Código de la oficina productora (máximo 6 dígitos)", required = true)
            @RequestParam(value = "codigo_oficina_productora", required = true) String codigo_oficina_productora,
            @RequestParam(value = "codigo_serie", required = false) Optional<String> codigoSerie,
            @RequestParam(value = "codigo_subserie",required = false) Optional<String> codigoSubserie,

            HttpServletResponse response) {

        String PATRON = "^[0-9]{5}\\.\\d{1,3}(\\.\\d{1,3})?$";

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        String serieSubserie = "";
        try {
            // Validar que el parámetro tenga exactamente 6 caracteres
            if (codigo_oficina_productora.length() > 6) {
                return new ResponseEntity<>("El parámetro 'codigo_oficina_productora' no debe superar los 6 digitos", HttpStatus.BAD_REQUEST);
            }

            if(codigoSerie.isPresent()){
                if (!codigoSerie.get().matches(PATRON)) {
                    return ResponseEntity.badRequest().body("Formato inválido. Debe ser 'xxxxx.xx' ");
                }
                serieSubserie = codigoSerie.get();
            }
            if(codigoSubserie.isPresent()){
                if (!codigoSubserie.get().matches(PATRON)) {
                    return ResponseEntity.badRequest().body("Formato inválido. Debe ser 'xxxxx.xx.x' ");
                }
                serieSubserie = codigoSubserie.get();
            }


            Integer codigo = Integer.parseInt(codigo_oficina_productora);
            // Validar que el valor no supere la capacidad máxima de Integer
            if (codigo > Integer.MAX_VALUE) {
                return new ResponseEntity<>("El parámetro 'codigo_oficina_productora' supera la capacidad máxima de un número entero", HttpStatus.BAD_REQUEST);
            }
            log.info("Solicitud para obtener instrumentos. Código de la oficina productora: {}", codigo);

            Map<String, Object> instrumentos = instrumentoService.consultarInstrumentos(codigo_oficina_productora, serieSubserie);
            log.info("Respuesta de la solicitud para obtener instrumentos: {}", instrumentos);

            return new ResponseEntity<>(instrumentos, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("El parámetro 'codigo_oficina_productora' debe ser un número entero", HttpStatus.BAD_REQUEST);
        }
    }


}
