package com.prolinktic.sgdea.dtos.radicado;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RadicadoEntradaDTO {

    //inicio de campos nuevo consumo

    private int tipoRadicado;

    private String radicadoOrigen;

    private String fechaDocumento;

    private int medioRecepcion;

    private String asuntoRadicacion;

    private int identificadorSistema;

    private String idTramite;

    private String destinatarioDependencia;

    private String destinatarioUbicacionSede;

    private String destinatarioNombresApellidos;

    private int tipoDocumentoRemitente;

    private String numeroDocumentoRemitente;

    private int remitenteTipoPersona;

    private String nombreRemitente;

    private String remitenteDireccion;

    private int remitentePais;

    private int remitenteDepartamento;

    private int remitenteMunicipio;

    private String remitenteCorreoElectronico;

    private String remitenteTelefono;

    private int fondoRadicacion;

    private Double oficinaProductora;

    private String serieRadicacion;

    private String subserieRadicacion;

    private int tipoDocumentalRadicacion;

    private String tipoComunicacion;

    private String observacionPpal;

    private String descripcionAnexo; //este campo tipo texto va a ser un array json con los siguientes campos en string:
    //observacion, tipoDocumental


}




