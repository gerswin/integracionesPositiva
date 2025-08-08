package com.prolinktic.sgdea.dtos.OpenEtlDto.mapper;

import com.prolinktic.sgdea.dtos.OpenEtlDto.FacturacionDescargaDto;
import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;

import java.util.List;
import java.util.stream.Collectors;

public class FacturacionDescargaMapperManual {

    public static FacturacionDescargaDto toDto(FacturacionDescarga entity) {
        if (entity == null) return null;

        FacturacionDescargaDto dto = new FacturacionDescargaDto();
        dto.setOfe(entity.getOfe());
        dto.setProveedor(entity.getProveedor());
        dto.setTipo(entity.getTipo());
        dto.setPrefijo(entity.getPrefijo());
        dto.setConsecutivo(entity.getConsecutivo());
        dto.setCufe(entity.getCufe());
        dto.setFecha(entity.getFecha());
        dto.setHora(entity.getHora());
        dto.setValor(entity.getValor());
        dto.setMarca(entity.isMarca());

        return dto;
    }

    // Convierte una lista de entidades a lista de DTOs
    public static List<FacturacionDescargaDto> toDtoList(List<FacturacionDescarga> entities) {
        return entities.stream()
                .map(FacturacionDescargaMapperManual::toDto)
                .collect(Collectors.toList());
    }

    // Convierte de DTO a entidad (opcional)
    public static FacturacionDescarga toEntity(FacturacionDescargaDto dto) {
        if (dto == null) return null;

        FacturacionDescarga entity = new FacturacionDescarga();
        entity.setOfe(dto.getOfe());
        entity.setProveedor(dto.getProveedor());
        entity.setTipo(dto.getTipo());
        entity.setPrefijo(dto.getPrefijo());
        entity.setConsecutivo(dto.getConsecutivo());
        entity.setCufe(dto.getCufe());
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        entity.setValor(dto.getValor());
        entity.setMarca(dto.isMarca());

        return entity;
    }
}
