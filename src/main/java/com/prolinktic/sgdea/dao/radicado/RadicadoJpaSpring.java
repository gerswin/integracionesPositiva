package com.prolinktic.sgdea.dao.radicado;

import com.prolinktic.sgdea.model.Radicado.Radicado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface RadicadoJpaSpring
    extends PagingAndSortingRepository<Radicado, Long>, JpaSpecificationExecutor<Radicado> {


  public Radicado findByNumeroRadicado(String radicado);
}
