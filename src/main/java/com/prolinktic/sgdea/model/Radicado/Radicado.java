package com.prolinktic.sgdea.model.Radicado;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "radicado")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Radicado {


      @Id
      @Column(name = "id_radicado")

      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq_generator")
      @SequenceGenerator(name = "producto_seq_generator", sequenceName = "radicado_seq",
          allocationSize = 1)
      private Long idRadicado;


    @Column(name = "documento")
    private String documento;

    @Column(name = "entidad_personal_natural")
    private String entidadPersonalNatural;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "dignatario")
    private String dignatario;

    @Column(name = "id_continente")
    private Long idContinente;

    @Column(name = "id_departamento")
    private Long idDepartamento;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "telefono")
    private Long telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "id_pais")
    private Long idPais;

    @Column(name = "id_municipio")
    private Long idMunicipio;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "id_medio_recepcion")
    private Long idMedioRecepcion;

    @Column(name = "id_tipo_documental")
    private Long idTipoDocumental;

    @Column(name = "descripcion_anexos")
    private String descripcionAnexos;

    @Column(name = "numero_folios")
    private Integer numeroFolios;

    @Column(name = "numero_anexos")
    private Integer numeroAnexos;

    @Column(name = "fecha_radicado")
    private Date fechaRadicado;

    @Column(name = "numero_radicado")
    private String numeroRadicado;

    @Column(name = "id_tipo_documento")
    private Long idTipoDocumento;


}

