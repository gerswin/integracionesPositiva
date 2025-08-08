package com.prolinktic.sgdea.model.asociacionDependencia;

import com.prolinktic.sgdea.model.CCD.CCD;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "asociaciondependencia")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AsociacionDependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_asociaciondependencia")
    private Integer idAsociaciondependencia;

    @ManyToOne
    @JoinColumn(name = "id_ccd")
    private CCD idCcd;

    @ManyToOne
    @JoinColumn(name = "id_dependencia", referencedColumnName = "idseccionsubseccion")
    private SeccionSubSeccion dependencia;

}
