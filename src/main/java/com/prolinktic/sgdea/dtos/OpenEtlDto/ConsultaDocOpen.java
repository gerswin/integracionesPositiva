package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDocOpen {
    private List<DatoDocOpen> data;
    private String message;
    private List<String> errors;
}
