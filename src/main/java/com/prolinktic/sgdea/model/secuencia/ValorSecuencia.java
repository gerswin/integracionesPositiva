package com.prolinktic.sgdea.model.secuencia;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "valor_secuencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValorSecuencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor")
    private Integer id;
    @Column
    private String valor;
    @ManyToOne
    @JoinColumn(name = "id_tipo_secuencia")
    private TipoSecuenciaEntity tipoSecuencia;

}
