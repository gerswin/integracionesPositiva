package com.prolinktic.sgdea.model.secuencia;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tipo_secuencia")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoSecuenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_secuencia")
    private Integer Id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombre_corto")
    private String nombreCorto;
}
