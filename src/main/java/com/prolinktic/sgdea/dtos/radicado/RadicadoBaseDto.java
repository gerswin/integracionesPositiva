package com.prolinktic.sgdea.dtos.radicado;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RadicadoBaseDto {


  @NotNull
  private Long idTipoDocumento;

  @NotNull
  private String documento;

  @NotBlank
  private String entidadPersonalNatural;


  @NotBlank
  private String cargo;


  @NotBlank
  private String direccion;


  @NotBlank
  private String dignatario;


  @NotNull
  private Long idContinente;


  @NotNull
  private Long idDepartamento;

  @NotNull
  private Long idTipoRadicado;


  @NotBlank
  private String nombreCompleto;

  @NotNull
  private Long telefono;

  @Email
  private String email;

  @NotNull
  private Long idPais;

  @NotNull
  private Long idMunicipio;

  @NotNull
  private String asunto;

  @NotNull
  private Long idMedioRecepcion;

  @NotNull
  @Min(0)
  private Long idTipoDocumental;

  @NotNull
  private String descripcionAnexos;

  @NotNull
  @Min(0)
  private Integer numeroFolios;

  @NotNull
  @Min(0)
  private Integer numeroAnexos;
}
