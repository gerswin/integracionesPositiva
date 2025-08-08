package com.prolinktic.sgdea.dtos.OpenEtlDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasificacionDocumentoRequestDto {
//    @NotBlank(message = "La clasificación es obligatoria")

    @Schema(description = "Clasificación del documento", example = "CRM", allowableValues = {"CRM", "CORE", "SGDEA", "CUIDA2"})
    private ClasificacionDocumento quienClasifica;

    @NotNull(message = "El archivo XML es obligatorio")
    private MultipartFile xmlfile;
    private MultipartFile pdfile;

    @NotNull(message = "El ofe es obligatorio")
    private String ofeIdentificacion;

    @NotNull(message = "El proIdentificacion es obligatorio")
    private String proIdentificacion;

    @NotNull(message = "El tipoDocumentoFAC es obligatorio")
    @Schema(description = "Tipo de documento", example = "FC", allowableValues = {"FC", "ND", "NC"})
    private TipoDocumentoFAC tipoDocumentoFAC;

    @NotNull(message = "El rfaPrefijo es obligatorio")
    @Schema(description = "Ejemplo Prefijo", example = "SETT")
    private String rfaPrefijo;

    @NotNull(message = "El cdoConsecutivo es obligatorio")
    private String cdoConsecutivo;

    @NotNull(message = "El cdoFecha es obligatorio")
    private String cdoFecha;

    @Schema(description = "Clasificación", example = "CUENTA_MEDICA", allowableValues = {"CUENTA_MEDICA", "COMISIONES", "FACTURA_ADMINISTRATIVA", "OTROS"})
    private Clasificacion clasificacion;
}
