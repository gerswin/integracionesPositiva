package com.prolinktic.sgdea.controller.usuarios;
import com.prolinktic.sgdea.dtos.Usuario.UsuarioDto;
import com.prolinktic.sgdea.service.Usuarios.IUsuariosService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Usuarios Firma ", description = "Consulta Imagen de la firma por usuario ")
@RequestMapping("usuarios/")
public class UsuarioController {

    @Autowired
    private IUsuariosService usuariosService;

    @GetMapping(value = "firma/{identificacion}")
    public UsuarioDto ObtenerFirmaUsuario(HttpServletResponse response, @NotNull HttpServletRequest request, @PathVariable("identificacion") @NotBlank String identificacion) throws Exception {

        return usuariosService.ObtenerFirma(identificacion);
    }
}
