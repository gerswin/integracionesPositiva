package com.prolinktic.sgdea.dtos.serie;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class SerieSubserieDTO {

    private Integer id_subserie;
    private String nombre_subserie;
    private Integer id_ccd;
    public SerieSubserieDTO(Integer idSubserie, String nombreSubserie, Integer idCcd) {
        this.id_subserie = idSubserie;
        this.nombre_subserie = nombreSubserie;
        this.id_ccd = idCcd;
    }

}
