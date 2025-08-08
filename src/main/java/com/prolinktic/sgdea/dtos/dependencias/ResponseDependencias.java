package com.prolinktic.sgdea.dtos.dependencias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDependencias {
    String mensaje;
    String fecha_recepcion;
    String id_transaccion;
    Number status;
    List<DataDependencias> Data;
}
