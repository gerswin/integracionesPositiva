package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListFacDes {
    private String status;
    private String message;
    private int total;
    private List<FacturacionDescargaDto> data;
}
