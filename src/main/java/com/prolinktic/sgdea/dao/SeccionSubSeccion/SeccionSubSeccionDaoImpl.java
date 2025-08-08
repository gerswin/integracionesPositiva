package com.prolinktic.sgdea.dao.SeccionSubSeccion;

import com.prolinktic.sgdea.dtos.instrumento.InstrumentoResponse;
import com.prolinktic.sgdea.dtos.instrumento.TipoDocumentoDTOEx;
import com.prolinktic.sgdea.dtos.seccionsubseccion.RequestSeccion;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SeccionSubSeccionDaoImpl implements SeccionSubSeccionDao {

    @Autowired
    SeccionSubSeccionJpaSpring SeccionSubSeccion;

    @Override
    public com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion recuperarSeccionSubSeccion(int id) {
        return SeccionSubSeccion.findById(id).orElse(null);
    }

    @Override
    public List<com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion> recuperarSeccionSubSeccionPadre(int idPadre) {
        return SeccionSubSeccion.findByIdPadre(idPadre);
    }

    public Optional<SeccionSubSeccion>recuperarSeccionSubSeccionCod(String des, String cod) {

        if(cod.isEmpty()){
            cod=null;
        }
        if(des.isEmpty()){
            des=null;
        }
        return SeccionSubSeccion.findByCod(des,cod);
    }
    @Override
    public void agregarSeccionSubSeccion(SeccionSubSeccion seccionSubSeccion) {
        SeccionSubSeccion.save(seccionSubSeccion);
    }

    @Override
    public List<SeccionSubSeccion> devolverSeccionSubSeccion() {

        return SeccionSubSeccion.findAll();
    }

    @Override
    public void actualizarSeccionSubSeccion(SeccionSubSeccion seccionSubSeccion) {
        SeccionSubSeccion.save(seccionSubSeccion);
    }

    @Override
    public void eliminarSeccionSubSeccion(int id) {
        SeccionSubSeccion.deleteById(id);
    }

    @Override
    public Integer agregarSeccionSubSeccionVlId(SeccionSubSeccion seccionSubSeccion) {
        SeccionSubSeccion guardar=  SeccionSubSeccion.save(seccionSubSeccion);
        return guardar.getIdSeccionSubSeccion();
    }

    @Override
    public Page<SeccionSubSeccion> findAllPaged(Pageable pageable, RequestSeccion filtro) {

        // Crea una instancia de Specification basada en los filtros
        Specification<SeccionSubSeccion> specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> listaDeintrucciones = new ArrayList<>();


            if (filtro.getCodigoDependencia() != null) {
                listaDeintrucciones.add(criteriaBuilder.like(root.get("codigo"),"%"+
                        filtro.getCodigoDependencia()+"%"));
            }

            if (filtro.getNombreDependencia() != null) {
                listaDeintrucciones.add(criteriaBuilder.like(root.get("nombre"),"%"+
                        filtro.getNombreDependencia()+"%"));
            }

            if (filtro.getEstado() != null) {
                listaDeintrucciones.add(criteriaBuilder.equal(root.get("estado"),
                        filtro.getEstado()));
            }

            return criteriaBuilder.and(listaDeintrucciones.toArray(new Predicate[listaDeintrucciones.size()]));

        };

        // Ejecuta la consulta utilizando el repositorio y la especificación
        Page<SeccionSubSeccion> seccionSubSeccionPage = SeccionSubSeccion.findAll(specification, pageable);

        return seccionSubSeccionPage;
    }

    @Override
    public List<SeccionSubSeccion> devolverSeccionSubSeccionCCD() {

        return SeccionSubSeccion.findSeccionSubSeccions();
    }

    @Override
    public List<Object> devolverPadreIns(String codigo, String codigoSerieSubserie) {
        return SeccionSubSeccion.findPadre(codigo,codigoSerieSubserie);
    }

    @Override
    public List<TipoDocumentoDTOEx> devolverTipoDIns(String codigo, String nombre) {
        if(nombre ==""){
            nombre=null;
        }
        return SeccionSubSeccion.findTipoDocumento(nombre,codigo);
    }

    @Override
    public List<com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion> actualizacionMasiva(List<SeccionSubSeccion> listado) {
        return SeccionSubSeccion.saveAll(listado);
    }

}
