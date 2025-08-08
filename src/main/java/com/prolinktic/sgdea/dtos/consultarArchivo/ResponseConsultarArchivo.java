package com.prolinktic.sgdea.dtos.consultarArchivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConsultarArchivo {
    String Id_documento;
    String mensaje;
    String fecha_recepcion;
    String id_transaccion;
    Number status;
    String Documento;
}
