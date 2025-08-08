package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFacDes {
    private String mensaje;
    private int status;
    private String pdf;
    private String xml;
}
