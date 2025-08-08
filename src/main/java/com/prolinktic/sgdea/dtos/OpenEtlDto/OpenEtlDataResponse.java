package com.prolinktic.sgdea.dtos.OpenEtlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenEtlDataResponse<T> {
    private T data;
}