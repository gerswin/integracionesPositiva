package com.prolinktic.sgdea.dtos.radicado;

import lombok.Data;

@Data
public class RadicadoSearchDto extends RadicadoListDto {

  private Long idTipoDocumento;

  private String documento;

  private String entidadPersonalNatural;

  private String cargo;


  private String direccion;

  private String dignatario;

  private Long idContinente;

  private Long idDepartamento;



  private Long idTipoRadicado;

  private String nombreCompleto;

  private Long telefono;


  private String email;

  private Long idPais;

  private Long idMunicipio;

  private String asunto;


  private Long idMedioRecepcion;

  private Long idTipoDocumental;

  private String descripcionAnexos;

  private Integer numeroFolios;


  private Integer numeroAnexos;
}
