package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

import java.util.List;

@Data
public class ProveedorDTO {
    private int pro_id;
    private String pro_identificacion;
    private String pro_id_personalizado;
    private String pro_razon_social;
    private String pro_nombre_comercial;
    private String pro_primer_apellido;
    private String pro_segundo_apellido;
    private String pro_primer_nombre;
    private String pro_otros_nombres;
    private int tdo_id;
    private int toj_id;
    private int pai_id;
    private int dep_id;
    private int mun_id;
    private int ofe_id;
    private int cpo_id;
    private String pro_direccion;
    private String pro_telefono;
    private int pai_id_domicilio_fiscal;
    private int dep_id_domicilio_fiscal;
    private int mun_id_domicilio_fiscal;
    private int cpo_id_domicilio_fiscal;
    private String pro_direccion_domicilio_fiscal;
    private String pro_correo;
    private int rfi_id;
    private String ref_id;
    private String pro_matricula_mercantil;
    private String pro_correos_notificacion;
    private String pro_usuarios_recepcion;
    private String pro_prestacion_servicios;
    private String fecha_creacion;
    private String fecha_modificacion;
    private int usuario_creacion;
    private String estado;
    private String nombre_completo;

    private UsuarioDTO get_usuario_creacion;
    private ConfiguracionOfeDTO get_configuracion_obligado_facturar_electronicamente;
    private ParametroDTO get_parametro_tipo_documento;
    private ParametroDTO get_parametro_pais;
    private ParametroDTO get_parametro_departamento;
    private ParametroDTO get_parametro_municipio;
    private ParametroDTO get_parametro_tipo_organizacion_juridica;
    private ParametroDTO get_regimen_fiscal;
    private List<ResponsabilidadFiscalDTO> get_responsabilidad_fiscal;
    private ParametroDTO get_codigo_postal;
    private ParametroDTO get_parametro_domicilio_fiscal_pais;
    private ParametroDTO get_parametro_domicilio_fiscal_departamento;
    private ParametroDTO get_parametro_domicilio_fiscal_municipio;
    private ParametroDTO get_codigo_postal_domicilio_fiscal;
    private List<Object> get_usuarios_portales;
}
