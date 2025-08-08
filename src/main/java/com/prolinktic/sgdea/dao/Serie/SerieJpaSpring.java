package com.prolinktic.sgdea.dao.Serie;

import com.prolinktic.sgdea.dtos.serie.SerieSubserieDTO;
import com.prolinktic.sgdea.model.Serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieJpaSpring extends JpaRepository<Serie, Integer> , JpaSpecificationExecutor<Serie> {


    @Query(nativeQuery = true, value = "WITH RECURSIVE RecursiveTable AS (" +
            "SELECT t2.id_seriesubserie AS child_id, t2.descripcion AS child_descripcion, t.id_padre AS parent_id, t.descripcion AS parent_descripcion,t.codigo AS codigov " +
            "FROM serie t " +
            " INNER JOIN serie t2 ON t.id_seriesubserie = t2.id_padre" +
            " WHERE t2.id_seriesubserie = :startId " +
            "UNION ALL " +
            "SELECT t.id_seriesubserie AS child_id, t.descripcion AS child_descripcion, t.id_padre AS parent_id, rt.parent_descripcion AS parent_descripcion,t.codigo AS codigov " +
            "FROM serie t " +
            "INNER JOIN RecursiveTable rt ON t.id_seriesubserie = rt.parent_id " +
            ")" +
            "SELECT rt.child_id AS id_seriesubserie, rt.child_descripcion AS descripcion_hijo, rt.parent_id AS id_padre, rt.parent_descripcion AS descripcion_padre ,rt.codigov AS codigo" +
            " FROM RecursiveTable rt")
    List<Object[]> getRecursiveResults(@Param("startId") Integer startId);


    @Query(nativeQuery = true, value = "WITH RECURSIVE RecursiveTable AS (" +
            " SELECT t.id_seriesubserie AS parent_id, t.descripcion AS parent_descripcion, t.id_seriesubserie AS child_id, t2.descripcion AS child_descripcion,t2.codigo AS codigoValor" +
            " FROM serie t" +
            " INNER JOIN serie t2 ON t.id_seriesubserie = t2.id_padre" +
            " WHERE ( :des IS NULL OR t.descripcion =  CAST( :des AS varchar(255)) ) " +
            " AND ( :codigo IS NULL OR t.codigo =CAST( :codigo AS varchar(255))) " +
            " UNION ALL" +
            " SELECT rt.parent_id, rt.parent_descripcion, t.id_seriesubserie, t.descripcion, t.codigo" +
            " FROM RecursiveTable rt" +
            " INNER JOIN serie t ON rt.child_id = t.id_padre) " +
            " SELECT child_id AS id_seriesubserie,CAST(child_descripcion AS varchar(255)) AS descripcion_hijo, parent_id AS id_padre, CAST(parent_descripcion AS varchar(255)) AS descripcion_padre, codigoValor AS codigo" +
            " FROM RecursiveTable")
    List<Object[]> getRecursiveResultsDes(@Param("codigo") String codigo, @Param("des") String des);

    @Query(nativeQuery = true, value ="SELECT t.id_seriesubserie" +
            " FROM serie t" +
            " WHERE   (:codigo IS NULL OR t.codigo LIKE  CONCAT('%', :codigo, '%'))")
    Integer getId(@Param("codigo") String codigo);

    List<Serie> findByPadreNull();

    @Query(nativeQuery = true, value = "WITH RECURSIVE serie_hierarchy AS ( " +
            "                       SELECT ss.* " +
            "                       FROM serie ss " +
            "                     JOIN ccd c on ss.id_seriesubserie = c.id_seriesubserie " +
            "                      JOIN asociaciondependencia a on c.id_ccd = a.id_ccd " +
            "                       UNION ALL " +
            "                       SELECT s.* " +
            "                      FROM serie s " +
            "                       JOIN serie_hierarchy sh ON s.id_seriesubserie = sh.id_padre " +
            ")SELECT * " +
            "FROM serie_hierarchy")
    List<Serie> findSeriesWithChildren();

    @Query("SELECT new com.prolinktic.sgdea.dtos.serie.SerieSubserieDTO(s.idSeriesubserie, s.descripcion, c.idCcd) " +
            " FROM Serie s left outer join CCD c on s.idSeriesubserie = c.idSerieSubserie.idSeriesubserie " +
            "            JOIN AsociacionDependencia a on c.idCcd = a.idCcd.idCcd" +
            "            WHERE  s.padre.idSeriesubserie = :idPadre and s.estados = :estados")
    List<SerieSubserieDTO> findByEstadosAndPadres(@Param("estados") Boolean estados, @Param("idPadre") Integer idPadre);

    @Query("SELECT new com.prolinktic.sgdea.dtos.instrumento.subSerieInsDto(s.codigo, s.descripcion) " +
            " FROM Serie s" +
            " WHERE  s.padre.idSeriesubserie = :idPadre")
    List<com.prolinktic.sgdea.dtos.instrumento.subSerieInsDto> findByPadre( @Param("idPadre") Integer idPadre);

    @Query("SELECT new com.prolinktic.sgdea.dtos.instrumento.serieInsDto(s.codigo, s.descripcion,s.idSeriesubserie) " +
            "FROM Serie s " +
            "WHERE s.idSeriesubserie IN :idPadres")
    List<com.prolinktic.sgdea.dtos.instrumento.serieInsDto> findSerie(@Param("idPadres") List<Integer> idPadres);

}
