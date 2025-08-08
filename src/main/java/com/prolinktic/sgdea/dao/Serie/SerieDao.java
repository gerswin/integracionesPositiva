package com.prolinktic.sgdea.dao.Serie;

import com.prolinktic.sgdea.dtos.serie.SerieDTO;
import com.prolinktic.sgdea.dtos.serie.SerieFlt;
import com.prolinktic.sgdea.dtos.serie.SerieSubserieDTO;
import com.prolinktic.sgdea.model.Serie.Serie;

import java.util.List;
import java.util.Optional;

public interface SerieDao {

    List<Object[]> obtenerHijosId( Integer startId);

    List<Object[]> obtenerHijosDs(String codigo, String descripcion);

    Optional<Serie> recuperarSerieById(int id);

    List<Serie> devolverAllSerie();

    void agregarSerie(Serie serie);

    void actualizarSerie(Serie serie);

    void eliminarSerie(int id);

    Integer recuperarSerieByCod(String cod);

    Integer agregarSerieiD(Serie serie);
    List<Serie> devolverSerie();
    List<Serie>findByPadreNull();
    Serie recuperarSerie(int id);
    List<SerieDTO> getSeriesWithChildren();
    List<SerieSubserieDTO> getHijosDeSerie(Integer idPadre);
    List<com.prolinktic.sgdea.dtos.instrumento.serieInsDto> devolverSerieIns(List<Integer> idPadre);

    List<com.prolinktic.sgdea.dtos.instrumento.subSerieInsDto>  devolverSubIns(Integer idPadre);

    List<Serie> actualizacionMasiva(List<Serie> listado);
    List<Serie> buscarSeriePorfiltro(SerieFlt serieFlt);

}
