package com.prolinktic.sgdea.dao.radicado;

import com.prolinktic.sgdea.dtos.radicado.RadicadoSearchDto;
import com.prolinktic.sgdea.model.Radicado.Radicado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



@Repository
public class RadicadoDaoImpl implements RadicadoDao {

  @Autowired
  RadicadoJpaSpring jpaSpring;

  @Override
  public Radicado create(Radicado radicado) {
    return jpaSpring.save(radicado);
  }


  @Override
  public void update(Radicado radicado) {
    // TODO Auto-generated method stub

  }

  @Override
  public Page findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
    return jpaSpring.findAll(pageable);
  }

  @Override
  public Optional<Radicado> findById(Long id) {

    return jpaSpring.findById(id);
  }

  @Override
  public Optional<Radicado> findByConsecutive(Long consecutive) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }


  @Override
  public Page findBySearch(Pageable pageable, RadicadoSearchDto searchDto) {


    return jpaSpring.findAll((root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      Class<?> dtoClass = searchDto.getClass();
      Field[] fields = dtoClass.getDeclaredFields();

      System.out.println("=====================================");
      System.out.print(fields.length);
      System.out.println("=====================================");
      for (Field field : fields) {
        field.setAccessible(true);
        String fieldName = field.getName();
        Object fieldValue;
        try {
          fieldValue = field.get(searchDto);

          if (fieldValue != null) {
            System.out.println("Campo: '" + fieldName + "', Valor: '" + fieldValue + "'");

            predicates.add(builder.equal(root.get(fieldName), fieldValue));
          }
        } catch (IllegalArgumentException | IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }


      }
      System.out.println("predicates: " + Arrays.toString(predicates.toArray()));
      // Combina los predicados con un operador lógico AND
      return builder.and(predicates.toArray(new Predicate[0]));
    }, pageable);
  }


  @Override
  public Radicado findByNumeroRadicado(String numeroRadicado) {
    return jpaSpring.findByNumeroRadicado(numeroRadicado);
  }



}


