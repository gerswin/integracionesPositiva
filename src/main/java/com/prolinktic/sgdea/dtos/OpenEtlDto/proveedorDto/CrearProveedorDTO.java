package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

import java.util.List;

@Data
public class CrearProveedorDTO {
    private String ofe_identificacion;
    private String pro_identificacion;
    private String pro_razon_social;
    private String pro_nombre_comercial;
    private String pro_primer_apellido;
    private String pro_segundo_apellido;
    private String pro_primer_nombre;
    private String pro_otros_nombres;
    private String tdo_codigo;
    private String toj_codigo;
    private String pai_codigo;
    private String dep_codigo;
    private String mun_codigo;
    private String cpo_codigo;
    private String pro_direccion;
    private String pai_codigo_domicilio_fiscal;
    private String dep_codigo_domicilio_fiscal;
    private String mun_codigo_domicilio_fiscal;
    private String cpo_codigo_domicilio_fiscal;
    private String pro_direccion_domicilio_fiscal;
    private String pro_telefono;
    private String pro_correo;
    private String rfi_codigo;
    private List<String> ref_codigo; // Porque ref_codigo viene como array
    private String pro_matricula_mercantil;
    private String pro_correos_notificacion;
}
