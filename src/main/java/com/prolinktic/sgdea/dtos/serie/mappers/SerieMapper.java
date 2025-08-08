package com.prolinktic.sgdea.dtos.serie.mappers;

import com.prolinktic.sgdea.dtos.serie.SerieDTO;
import com.prolinktic.sgdea.model.Serie.Serie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface SerieMapper {

    SerieMapper INSTANCE = Mappers.getMapper(SerieMapper.class);

    @Mapping(target = "idSerie", source = "idSeriesubserie")
    @Mapping(target = "descripcion", source = "descripcion")
    SerieDTO serieToDTO(Serie serie);

    default List<SerieDTO> mapSerieListToDTOList(List<Serie> serieList) {
        return serieList.stream()
                .filter(serie -> serie != null && serie.getPadre() == null)
                .map(this::serieToDTO)
                .collect(Collectors.toList());
    }
}
