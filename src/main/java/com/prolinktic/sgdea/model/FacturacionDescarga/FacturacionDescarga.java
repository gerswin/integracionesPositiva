package com.prolinktic.sgdea.model.FacturacionDescarga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "facturacion_descarga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQuery(
        name = "ListarFacturacion",
        procedureName = "ListarFacturacion",
        resultClasses = FacturacionDescarga.class,
        parameters = {
                @StoredProcedureParameter(name = "p_fecha", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "p_prefijo", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "p_consecutivo", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "p_proveedor", type = String.class, mode = ParameterMode.IN)
        }
)
public class FacturacionDescarga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fac_des")
    private int idFacDes;
    @Column(name = "ofe")
    private String ofe;
    @Column(name="proveedor")
    private String proveedor;
    @Column(name="tipo")
    private String tipo;
    @Column(name="prefijo")
    private String prefijo;
    @Column(name="consecutivo")
    private String consecutivo;
    @Column(name="cufe")
    private String cufe;
    @Column(name="fecha")
    private String fecha;
    @Column(name="hora")
    private String hora;
    @Column(name="valor")
    private Double valor;
    @Column(name="marca")
    private boolean marca;

}
