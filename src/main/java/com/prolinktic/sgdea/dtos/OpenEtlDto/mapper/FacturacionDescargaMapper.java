package com.prolinktic.sgdea.dtos.OpenEtlDto.mapper;

import com.prolinktic.sgdea.dtos.OpenEtlDto.FacturacionDescargaDto;
import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacturacionDescargaMapper {
    FacturacionDescargaDto toDto(FacturacionDescarga facturacionDescarga);
    List<FacturacionDescargaDto> toDtoList(List<FacturacionDescarga> facturacionDescargas);
}