package com.prolinktic.sgdea.service.loginUsuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;

@Service
public class LoginUsuarioServiceImpl implements LoginUsuarioService{

    @Autowired
    EntityManager em;

    @Override
    public boolean login(String usuario, String passwd) {
        //inicio de operaciones extras con las credenciales
        //invertir string
        String usuarioInvertido = String.valueOf(new StringBuilder(usuario).reverse());
        String passwdInvertido = String.valueOf(new StringBuilder(passwd).reverse());

        //decodificar de base 64 a String
        byte[] bytesDecodificadosUser = Base64.getDecoder().decode(usuarioInvertido);
        String usuarioDecodeBase64 = new String(bytesDecodificadosUser);

        byte[] bytesDecodificadosPasswd = Base64.getDecoder().decode(passwdInvertido);
        String passwdDecodeBase64 = new String(bytesDecodificadosPasswd);
        //fin de operaciones extras con las credenciales

        String queryLogin = "select * from usuariosservicios where nombreusuario = '"+usuarioDecodeBase64 + "' and passwdusuario = '"+passwdDecodeBase64+ "' and estado = true";

        //EntityManager usa los datos de la conexion de la BD que esta mapeada en el application.properties

        //si se usa createNativeQuery las tablas no deben de estar mapeadas con spring Boot
         Query query = em.createNativeQuery(queryLogin);

        List<String> listaResultado = query.getResultList();

        if(listaResultado.size() > 0){
            return true; //login ok
        }else{
            return false; //login fail
        }
    }
}
