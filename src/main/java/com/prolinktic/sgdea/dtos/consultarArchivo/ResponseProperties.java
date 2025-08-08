package com.prolinktic.sgdea.dtos.consultarArchivo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProperties {

    private int remitentePais;

    private String remitenteDireccion;

    private int medioRecepcion;

    private int identificadorSistema;

    private String numeroDocumento;

    private String oficinaProductora;

    private String serie;

    private int remitenteMunicipio;

    private String remitenteTelefono;

    private Date fechaDocumento;

    private int tipoDocumental;

    private String descripcionAnexo;

    private int tipoDocumento;

    private String asuntoRadicacion;

    private String idTramite;

    private int remitenteTipoPersona;

    private String tipoComunicacion;

    private String subserie;

    private Date horaDocumento;

    private Date fechaOriginal;

    private String numeroRadicado;

    private String destinatarioNombresApellidos;

    private String nombreRemitente;

    private int remitenteDepartamento;

    private String remitenteCorreoElectronico;

    private int fondo;

    private String observacionPpal;

    @JsonProperty("remitenteDireccion")
    public String getRemitenteDireccion() {
        return remitenteDireccion;
    }

    @JsonProperty("medioRecepcion")
    public int getMedioRecepcion() {
        return medioRecepcion;
    }

    @JsonProperty("identificadorSistema")
    public int getIdentificadorSistema() {
        return identificadorSistema;
    }

    @JsonProperty("numeroDocumento")
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    @JsonProperty("oficinaProductora")
    public String getOficinaProductora() {
        return oficinaProductora;
    }

    @JsonProperty("serie")
    public String getSerie() {
        return serie;
    }

    @JsonProperty("remitenteMunicipio")
    public int getRemitenteMunicipio() {
        return remitenteMunicipio;
    }

    @JsonProperty("remitenteTelefono")
    public String getRemitenteTelefono() {
        return remitenteTelefono;
    }

    @JsonProperty("fechaDocumento")
    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    @JsonProperty("tipoDocumental")
    public int getTipoDocumental() {
        return tipoDocumental;
    }

    @JsonProperty("descripcionAnexo")
    public String getDescripcionAnexo() {
        return descripcionAnexo;
    }

    @JsonProperty("tipoDocumento")
    public int getTipoDocumento() {
        return tipoDocumento;
    }

    @JsonProperty("asuntoRadicacion")
    public String getAsuntoRadicacion() {
        return asuntoRadicacion;
    }

    @JsonProperty("idTramite")
    public String getIdTramite() {
        return idTramite;
    }

    @JsonProperty("remitenteTipoPersona")
    public int getRemitenteTipoPersona() {
        return remitenteTipoPersona;
    }

    @JsonProperty("tipoComunicacion")
    public String getTipoComunicacion() {
        return tipoComunicacion;
    }

    @JsonProperty("subserie")
    public String getSubserie() {
        return subserie;
    }

    @JsonProperty("horaDocumento")
    public Date getHoraDocumento() {
        return horaDocumento;
    }

    @JsonProperty("fechaOriginal")
    public Date getFechaOriginal() {
        return fechaOriginal;
    }

    @JsonProperty("numeroRadicado")
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    @JsonProperty("destinatarioNombresApellidos")
    public String getDestinatarioNombresApellidos() {
        return destinatarioNombresApellidos;
    }

    @JsonProperty("nombreRemitente")
    public String getNombreRemitente() {
        return nombreRemitente;
    }

    @JsonProperty("remitenteDepartamento")
    public int getRemitenteDepartamento() {
        return remitenteDepartamento;
    }

    @JsonProperty("remitenteCorreoElectronico")
    public String getRemitenteCorreoElectronico() {
        return remitenteCorreoElectronico;
    }

    @JsonProperty("fondo")
    public int getFondo() {
        return fondo;
    }

    @JsonProperty("observacionPpal")
    public String getObservacionPpal() {
        return observacionPpal;
    }

    @JsonProperty("remitentePais")
    public int getRemitentePais() {
        return remitentePais;
    }

    @JsonProperty("proltk:remitentePais")
    public void setRemitentePais(int remitentePais) {
        this.remitentePais = remitentePais;
    }

    @JsonProperty("proltk:remitenteDireccion")
    public void setRemitenteDireccion(String remitenteDireccion) {
        this.remitenteDireccion = remitenteDireccion;
    }

    @JsonProperty("proltk:medioRecepcion")
    public void setMedioRecepcion(int medioRecepcion) {
        this.medioRecepcion = medioRecepcion;
    }

    @JsonProperty("proltk:identificadorSistema")
    public void setIdentificadorSistema(int identificadorSistema) {
        this.identificadorSistema = identificadorSistema;
    }

    @JsonProperty("proltk:numero_documento")
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @JsonProperty("proltk:oficinaProductora")
    public void setOficinaProductora(String oficinaProductora) {
        this.oficinaProductora = oficinaProductora;
    }

    @JsonProperty("proltk:serie")
    public void setSerie(String serie) {
        this.serie = serie;
    }

    @JsonProperty("proltk:remitenteMunicipio")
    public void setRemitenteMunicipio(int remitenteMunicipio) {
        this.remitenteMunicipio = remitenteMunicipio;
    }

    @JsonProperty("proltk:telefono_remitente")
    public void setRemitenteTelefono(String remitenteTelefono) {
        this.remitenteTelefono = remitenteTelefono;
    }

    @JsonProperty("proltk:fecha_recepcion")
    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    @JsonProperty("proltk:tipodocumental")
    public void setTipoDocumental(int tipoDocumental) {
        this.tipoDocumental = tipoDocumental;
    }

    @JsonProperty("proltk:descripcion_anexo")
    public void setDescripcionAnexo(String descripcionAnexo) {
        this.descripcionAnexo = descripcionAnexo;
    }

    @JsonProperty("proltk:tipo_documento")
    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @JsonProperty("proltk:asunto")
    public void setAsuntoRadicacion(String asuntoRadicacion) {
        this.asuntoRadicacion = asuntoRadicacion;
    }

    @JsonProperty("proltk:idTramite")
    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    @JsonProperty("proltk:remitenteTipoPersona")
    public void setRemitenteTipoPersona(int remitenteTipoPersona) {
        this.remitenteTipoPersona = remitenteTipoPersona;
    }

    @JsonProperty("proltk:tipo_comunicacion")
    public void setTipoComunicacion(String tipoComunicacion) {
        this.tipoComunicacion = tipoComunicacion;
    }

    @JsonProperty("proltk:subserie")
    public void setSubserie(String subserie) {
        this.subserie = subserie;
    }

    @JsonProperty("proltk:hora_recepcion")
    public void setHoraDocumento(Date horaDocumento) {
        this.horaDocumento = horaDocumento;
    }

    @JsonProperty("proltk:fecha_original")
    public void setFechaOriginal(Date fechaOriginal) {
        this.fechaOriginal = fechaOriginal;
    }

    @JsonProperty("proltk:numero_radicado")
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    @JsonProperty("proltk:Apellidos_remitente")
    public void setDestinatarioNombresApellidos(String destinatarioNombresApellidos) {
        this.destinatarioNombresApellidos = destinatarioNombresApellidos;
    }

    @JsonProperty("proltk:nombres_remitente")
    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    @JsonProperty("proltk:departamento")
    public void setRemitenteDepartamento(int remitenteDepartamento) {
        this.remitenteDepartamento = remitenteDepartamento;
    }

    @JsonProperty("proltk:correo_electronico_remitente")
    public void setRemitenteCorreoElectronico(String remitenteCorreoElectronico) {
        this.remitenteCorreoElectronico = remitenteCorreoElectronico;
    }

    @JsonProperty("proltk:fondo")
    public void setFondo(int fondo) {
        this.fondo = fondo;
    }

    @JsonProperty("proltk:observacionPpal")
    public void setObservacionPpal(String observacionPpal) {
        this.observacionPpal = observacionPpal;
    }

}