package com.prolinktic.sgdea.model.tipoDocumental;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoDocumentalTRDVM {
    private int id_tipo_documental_TRD;
    private int id_soporteDocumentalDetalle;
    private int id_serieSubserie;
    private int id_tipo_documental;
    private String proceso;
    private String procedimiento;
}