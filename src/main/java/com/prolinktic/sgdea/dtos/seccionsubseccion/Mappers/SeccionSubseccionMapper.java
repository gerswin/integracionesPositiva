package com.prolinktic.sgdea.dtos.seccionsubseccion.Mappers;




import com.prolinktic.sgdea.dtos.seccionsubseccion.seccionSubseccionDTO;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")

public interface SeccionSubseccionMapper {

    SeccionSubseccionMapper INSTANCE = Mappers.getMapper(SeccionSubseccionMapper.class);
    @Mapping(target = "padre",ignore = true)
    @Mapping(target = "fondo",ignore = true)
    seccionSubseccionDTO seccionsubseccionToDTO(SeccionSubSeccion seccionSubSeccion);
    @Mapping(target = "padre",ignore = true)
    @Mapping(target = "fondo",ignore = true)
    SeccionSubSeccion dtoToseccionsubseccion(seccionSubseccionDTO seccionSubseccionDTO);
    @Mapping(target = "padre",ignore = true)
    @Mapping(target = "fondo",ignore = true)
    List<seccionSubseccionDTO> seccionsubseccionsToDTOs(List<SeccionSubSeccion> seccionSubSeccions);


}
