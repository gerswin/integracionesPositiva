package com.prolinktic.sgdea.dtos.buscarDocumento;

import com.prolinktic.sgdea.dtos.consultarArchivo.ResponseProperties;
import com.prolinktic.sgdea.dtos.radicado.Properties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Documento {

    private String id;
    private String nombre;
    private String descripcion;
    private ResponseProperties propiedades;
}
