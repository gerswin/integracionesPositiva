package com.prolinktic.sgdea.model.Empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa")
    private Integer idEmpresa;
    @Column(name = "nombre")
    private String nombre;
    private String direccion;
    private String telefono;

    @Column(name = "tiposociedad")
    private Integer tipoSociedad;
    @Column(name = "fechaconstitucion")
    private Date fechaContitucion;
    @Column(name = "actoadministrativoconstitucion")
    private String actoAdministrativoConstitucion;
    private String nit;
    private String email;
    //    @Column(name = "nombre_representacion_legal")
//    private String  nombreRepresentanteLegal;
    @Column(name = "telefonorepresentantelegal")
    private String telefonoRepresentanteLegal;
    @Column(name = "direccionrepresentantelegal")
    private String direccionRepresentanteLegal;
    @Column(name = "nacionalidadrepresentantelegal")
    private String nacionalidadRepesentanteLegal;
    @Column(name = "correorepresentantelegal")
    private String correoRepresentanteLegal;
    private Boolean estado;

}