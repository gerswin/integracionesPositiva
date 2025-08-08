package com.prolinktic.sgdea.dtos.OpenEtlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenEtlListDataResponse<T> {
    private List<T> data;
}