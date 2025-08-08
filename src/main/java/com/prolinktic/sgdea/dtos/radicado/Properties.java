package com.prolinktic.sgdea.dtos.radicado;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Properties {

    @JsonProperty("proltk:remitentePais") //ok
    private int remitentePais;

    @JsonProperty("proltk:remitenteDireccion") //ok
    private String remitenteDireccion;

    @JsonProperty("proltk:medioRecepcion") //ok
    private int medioRecepcion;

    @JsonProperty("proltk:identificadorSistema") //ok
    private int identificadorSistema;

    @JsonProperty("proltk:numeroDocumentoRemitente") //ok   este esta diferente en el modelo que se paso del xml
    private String numeroDocumento;

    @JsonProperty("proltk:oficinaProductora") //ok
    private int oficinaProductora;

    @JsonProperty("proltk:serieRadicacion") //ok este esta diferente vs el modelo del xml
    private String serie;

    @JsonProperty("proltk:remitenteMunicipio") //ok
    private int remitenteMunicipio;

    @JsonProperty("proltk:remitenteTelefono") //ok esta diferente en el modelo entregado del xml
    private String remitenteTelefono;

    @JsonProperty("proltk:fecha_recepcion") //ok
    private Date fechaDocumento;

    @JsonProperty("proltk:tipoDocumentalRadicacion") //ok diferente del modelo entregado
    private int tipoDocumental;

    @JsonProperty("proltk:descipcionAnexo") //ok diferente dek nodelo entregado
    private String descripcionAnexo;

    @JsonProperty("proltk:tipoDocumentoRemitente") //ok diferente del modelo entregado
    private int tipoDocumento;

    @JsonProperty("proltk:asuntoRadicacion") //ok diferente del modelo entregado
    private String asuntoRadicacion;

    @JsonProperty("proltk:idTramite") //ok
    private String idTramite;

    @JsonProperty("proltk:remitenteTipoPersona") //ok
    private int remitenteTipoPersona;

    //@JsonProperty("proltk:tipo_comunicacion") //este campo no esta en el nuevo modelo entregado de alfresco
    //private String tipoComunicacion;

    @JsonProperty("proltk:subserieRadicacion") //ok diferente del modelo entregado
    private String subserie;

    //@JsonProperty("proltk:hora_recepcion") //ok no esta en el nuevo modelo entregado
   // private Date horaDocumento;

    @JsonProperty("proltk:fechaDocumento") //ok diferente del modelo entregado
    private Date fechaOriginal;

    @JsonProperty("proltk:destinatarioNombresApellidos") //ok diferente del modelo entregado
    private String destinatarioNombresApellidos;

    @JsonProperty("proltk:nombreRemitente") //ok diferente del modelo entregado
    private String nombreRemitente;

    @JsonProperty("proltk:remitenteDepartamento") //ok diferente del modelo entregado
    private int remitenteDepartamento;

    @JsonProperty("proltk:remitenteCorreoElectronico")// ok diferente del modelo entregado
    private String remitenteCorreoElectronico;

    @JsonProperty("proltk:fondoRadicacion") //ok este esta diferente en el modelo que se paso del xml
    private int fondo;

    @JsonProperty("proltk:observacionPpal") //ok
    private String observacionPpal;


    //nuevas propiedades
    @JsonProperty("proltk:radicadoOrigen") //ok
    private String radicadoOrigen;

    @JsonProperty("proltk:tipoRadicado") //ok
    private String tipoRadicado;

    @JsonProperty("proltk:destinatarioDependencia") //ok
    private String destinatarioDependencia;

    @JsonProperty("proltk:destinatarioUbicacionSede") //ok
    private String destinatarioUbicacionSede;

    @JsonProperty("proltk:numero_radicado") //ok
    private String numeroRadicado;

    @JsonProperty("proltk:observacionRadicacion") //ok
    private String observacionRadicacion;

    @JsonProperty("proltk:nombreOriginalArchivo")
    private String nombreOrginalArchivo;//ok


}