package com.prolinktic.sgdea.dtos.instrumento;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListadoRadicadoResponse {

    @JsonProperty("codigo_serie")
    String codigoseccionsubseccion;
    @JsonProperty("nommbre_serie")
    String nombreseccionsubseccion;
    Tiporesponse tipo;
    Subserie subserie;

}
