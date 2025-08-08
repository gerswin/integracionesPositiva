package com.prolinktic.sgdea.controller.Expediente;

import com.prolinktic.sgdea.dtos.expediente.ExpedienteCerrarDto;
import com.prolinktic.sgdea.dtos.expediente.ResponseExpedienteCerrarDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prolinktic.sgdea.service.expediente.ExpedienteService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("expediente")
@Api(tags = "Expedientes", description = "Operaciones relacionadas con la gestion de expediente")
public class ExpedienteController {
    @Autowired
    ExpedienteService service;
    @PutMapping (value = "/cerrarExpedientes")
    @Operation(summary = "Cerrar Expedientes")
    public ResponseEntity<ResponseExpedienteCerrarDto> cerrarExpediente(ExpedienteCerrarDto request, HttpServletResponse response){

        Cookie sessionCookie = new Cookie("JSESSIONID", "9B9DAA8EFCCECFDBEB7E3E337871FE3A");
        sessionCookie.setHttpOnly(true); // Asegúrate de establecer otras opciones según tus necesidades
        sessionCookie.setSecure(true); // Esto se recomienda si estás utilizando "None"
        // sessionCookie.setSameSite("None"); // Cambia a "Strict" o "Lax" según tus necesidades
        response.addCookie(sessionCookie);

        try {
            ResponseExpedienteCerrarDto response2 = service.cerrarExpedientes(request);
            if (response2.getNombreArchivo().isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.ok(response2);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
