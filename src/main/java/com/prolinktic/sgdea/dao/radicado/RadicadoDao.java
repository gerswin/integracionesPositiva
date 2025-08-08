package com.prolinktic.sgdea.dao.radicado;

import com.prolinktic.sgdea.dtos.radicado.RadicadoSearchDto;
import com.prolinktic.sgdea.model.Radicado.Radicado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface RadicadoDao {

  Radicado create(Radicado instanceNumberGenerative);

  void update(Radicado instanceNumberGenerative);

  Page findAll(int page, int size);

  Optional<Radicado> findById(Long id);

  Optional<Radicado> findByConsecutive(Long consecutive);


  Page findBySearch(Pageable pageable, RadicadoSearchDto searchDto);


  Radicado findByNumeroRadicado(String numeroRadicado);
}
