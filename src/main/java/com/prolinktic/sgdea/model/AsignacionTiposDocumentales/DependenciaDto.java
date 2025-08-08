package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import java.util.List;

@Data
public class DependenciaDto {

    public List<SerieAsignacion> subSeries;

    public List<AsociacionTipoDocumental> TiposDocumentalesAsociados;
    public Integer idDependencia;
    public String Nombre;
    public  Integer tipo_soporte;
    public SerieAsignacion serieAsignacion;
    public List<TipoDocumentoAsignacion> tiposDocumentalesDisponibles;
}
