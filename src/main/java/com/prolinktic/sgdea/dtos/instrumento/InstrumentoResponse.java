package com.prolinktic.sgdea.dtos.instrumento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstrumentoResponse {

    String codigoseccionsubseccion;
    String nombreseccionsubseccion;
    Integer idseriesubserie;
    String nombresubserie;
    Integer idseriepadre;
    Integer idtipologiadocumental;
    String tiponombre;


}
