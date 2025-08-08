package com.prolinktic.sgdea.dtos.expediente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseExpedienteCerrarDto {
    int totalRegistros=0;
    int registrosCerrados=0;
    int registrosNovedad=0;
    String nombreArchivo;
    String archivo;
}
