package com.prolinktic.sgdea.model.codigodebarra;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tipo_codigos_de_barra")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCodigoBarra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tipo_codigo_barra")
    private String tipoCodigoBarra;
    private String descripcion;
    private boolean estado;


}
