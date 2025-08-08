package com.prolinktic.sgdea.dtos.codigodebarra.mapper;

import com.prolinktic.sgdea.dtos.codigodebarra.TipoCodigoDeBarraDTO;
import com.prolinktic.sgdea.model.codigodebarra.TipoCodigoBarra;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface TipoCodigoDeBarrasMapper {

    TipoCodigoDeBarrasMapper INSTANCE = Mappers.getMapper(TipoCodigoDeBarrasMapper.class);

    TipoCodigoDeBarraDTO tipoCodigodeBarratoDTO (TipoCodigoBarra tipoCodigoBarra);
    TipoCodigoBarra dtoToTipoCodigoBarra (TipoCodigoDeBarraDTO tipoCodigoDeBarraDTO);
    List<TipoCodigoBarra> dtosToEntities(List<TipoCodigoDeBarraDTO> dtos);
    List<TipoCodigoDeBarraDTO> entitiesToDtos(List<TipoCodigoBarra> entities);

}
