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
public class IntrumentosDto {
    private oficinaDto Oficina;
    List<InstrumentoDto> listado_radicado =new ArrayList<>();
}
