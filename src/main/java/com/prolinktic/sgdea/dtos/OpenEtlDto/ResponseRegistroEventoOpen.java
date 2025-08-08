package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRegistroEventoOpen {
    private String message;
    private List<String> exitosos;
    private List<String> fallidos;
}
