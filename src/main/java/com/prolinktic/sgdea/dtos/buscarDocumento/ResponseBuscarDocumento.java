package com.prolinktic.sgdea.dtos.buscarDocumento;

import com.prolinktic.sgdea.dtos.radicado.DescripcionAnexoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBuscarDocumento {
    private String idRadicado;
    private String mensaje;
    private int estado;
    List<Documento> documentos;
    List<DescripcionAnexoDTO> descripcionAnexoDTOList;

}
