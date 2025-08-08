package com.prolinktic.sgdea.service.Usuarios;

import com.prolinktic.sgdea.dtos.Usuario.UsuarioDto;

public interface IUsuariosService {

    UsuarioDto ObtenerFirma(String identificacion) throws Exception;

}
