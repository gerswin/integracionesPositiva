package com.prolinktic.sgdea.dao.Serie;

import com.prolinktic.sgdea.dtos.serie.SerieFlt;
import com.prolinktic.sgdea.model.Serie.Serie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
public class SerieSpecification {

    public static Specification<Serie> serieBusquedaPorFiltro(SerieFlt filtro){
        Specification<Serie> serieSpecification=(root, query, criteriaBuilder) -> {
            List<Predicate> predicates= new ArrayList<>();
            if (filtro.getId_seriesubserie()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idSeriesubserie"),filtro.getId_seriesubserie()));
            }
            if(filtro.getDescripcion()!=null){
                Expression<String> descripcionLowerCase=criteriaBuilder.lower(root.get("descripcion"));
                predicates.add(criteriaBuilder.like(descripcionLowerCase,"%"+filtro.getDescripcion().toLowerCase()+"%"));
            }
            if(filtro.getEstados()!=null){
                predicates.add(criteriaBuilder.equal(root.get("estados"),filtro.getEstados()));
            }
            if(filtro.getTipo_de_soporte()!=null){
                predicates.add(criteriaBuilder.equal(root.get("tipo_de_soporte"),filtro.getTipo_de_soporte()));
            }
            if(filtro.getObservacion()!=null){
                Expression<String> observacionLowerCase=criteriaBuilder.lower(root.get("observacion"));
                predicates.add(criteriaBuilder.like(observacionLowerCase,"%"+filtro.getObservacion().toLowerCase()+"%"));
            }
            if(filtro.getCodigo()!=null){
                Expression<String> codigoLowerCase=criteriaBuilder.lower(root.get("codigo"));
                predicates.add(criteriaBuilder.like(codigoLowerCase,"%"+filtro.getCodigo().toLowerCase()+"%"));
            }
            if (filtro.getDependencia()!=null){
                predicates.add(criteriaBuilder.equal(root.get("seccionSubSeccion").get("idSeccionSubSeccion"),filtro.getDependencia()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return serieSpecification;
    }
}
