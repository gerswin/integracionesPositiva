package com.prolinktic.sgdea.dao.Serie;

import com.prolinktic.sgdea.dtos.serie.SerieDTO;
import com.prolinktic.sgdea.dtos.serie.SerieFlt;
import com.prolinktic.sgdea.dtos.serie.SerieSubserieDTO;
import com.prolinktic.sgdea.dtos.serie.mappers.SerieMapper;
import com.prolinktic.sgdea.model.Serie.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SerieDaoImpl implements SerieDao {

    @Autowired
    SerieJpaSpring serieJpaSpring;

    SerieMapper serieMapper = SerieMapper.INSTANCE;

    @Override
    public List<Object[]> obtenerHijosId(Integer startId) {

        return serieJpaSpring.getRecursiveResults(startId);
    }

    @Override
    public List<Object[]> obtenerHijosDs(String codigo, String descripcion) {
        if (codigo.isEmpty()) {
            codigo = null;
        }
        if (descripcion.isEmpty()) {
            descripcion = null;
        }

        return serieJpaSpring.getRecursiveResultsDes(codigo, descripcion);
    }

    @Override
    public Optional<Serie> recuperarSerieById(int id) {
        return serieJpaSpring.findById(id);
    }

    @Override
    public Integer recuperarSerieByCod(String cod) {
        return serieJpaSpring.getId(cod);
    }

    @Override
    public List<Serie> devolverAllSerie() { //ok juan
        return serieJpaSpring.findAll();
    }

    @Override
    public void agregarSerie(Serie serie) {
        serieJpaSpring.save(serie);
    }

    @Override
    public Integer agregarSerieiD(Serie serie) {

        Serie guardar = serieJpaSpring.save(serie);
        return guardar.getIdSeriesubserie();
    }


    @Override
    public void actualizarSerie(Serie serie) {
        serieJpaSpring.save(serie);
    }

    @Override
    public void eliminarSerie(int id) {
        serieJpaSpring.deleteById(id);
    }

    @Override
    public List<Serie> devolverSerie() {

        return serieJpaSpring.findAll();
    }

    @Override
    public List<Serie> findByPadreNull() {

        return serieJpaSpring.findByPadreNull();
    }

    @Override
    public Serie recuperarSerie(int id) {
        return serieJpaSpring.findById(id).orElse(null);
    }

    @Override
    public List<SerieDTO> getSeriesWithChildren() {
        List<Serie> padre =serieJpaSpring.findSeriesWithChildren();
        return  serieMapper.mapSerieListToDTOList(padre);

    }

    @Override
    public List<SerieSubserieDTO> getHijosDeSerie(Integer idPadre) {
        boolean estados = true;
        return serieJpaSpring.findByEstadosAndPadres(estados, idPadre);

    }

    @Override
    public List<com.prolinktic.sgdea.dtos.instrumento.serieInsDto> devolverSerieIns(List<Integer> idPadre) {

        return serieJpaSpring.findSerie(idPadre);
    }
    @Override
    public List<com.prolinktic.sgdea.dtos.instrumento.subSerieInsDto>  devolverSubIns(Integer idPadre) {

        return serieJpaSpring.findByPadre(idPadre);
    }

    @Override
    public List<Serie> actualizacionMasiva(List<Serie> listado) {
        return serieJpaSpring.saveAll(listado);
    }
    @Override
    public List<Serie> buscarSeriePorfiltro(SerieFlt serieFlt) {
        Specification<Serie> filtro=SerieSpecification.serieBusquedaPorFiltro(serieFlt);
        return serieJpaSpring.findAll(filtro);
    }

}
