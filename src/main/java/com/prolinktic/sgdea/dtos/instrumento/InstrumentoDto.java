package com.prolinktic.sgdea.dtos.instrumento;

import com.prolinktic.sgdea.dtos.instrumento.TipoDocumentoDTOEx;
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
public class InstrumentoDto {

    String codigo_serie;
    String nombre_serie;
    List<subSerieInsDto> Subserie =new ArrayList<>();
    List<TipoDocumentoDTOEx> tipo =new ArrayList<>();

}
