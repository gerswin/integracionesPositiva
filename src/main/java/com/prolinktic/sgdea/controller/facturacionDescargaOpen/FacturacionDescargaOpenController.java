package com.prolinktic.sgdea.controller.facturacionDescargaOpen;

import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.exception.FacturacionException;
import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import com.prolinktic.sgdea.service.facturacionDescargaOpen.FacturacionDescargaOpen;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/facturacionDescarga")
@RestController
@Api(tags = "Facturación Management API", description = "Servicios Integración Facturación OpenEtlAPI")
public class FacturacionDescargaOpenController {
    @Autowired
    private FacturacionDescargaOpen facOpenservice;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMAT_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Operation(summary = "Listar facturas en openEtl", description = "Permite listar todas las facturas de open")
    @GetMapping(value = "/traerOpen")
    public ResponseEntity<ResponseListFacDes> listarFacturacionDescarga(@RequestParam("fecha_desde") String fechain,
            @RequestParam("fecha_hasta") String fechafin) throws ExecutionException, InterruptedException, TimeoutException {
        log.info("Entrando a Controller Listar facturas en openEtl");
        CompletableFuture<ResponseListFacDes>  futureResponse = facOpenservice.traerOpenAsync(fechain, fechafin, null, null, null);
        ResponseListFacDes resp = futureResponse.get(60, TimeUnit.SECONDS);

        if (resp.getStatus().equals("200")) {
            return ResponseEntity.ok(resp);
        } else if (resp.getStatus().equals("400")) {
            return ResponseEntity.status(400).body(resp);
        } else {
            return null;
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleListarFacException(Exception ex) {
        log.error("Error en listarFac - {} - Error: {}", LocalDateTime.now().format(FORMATTER), ex.getMessage());
        log.error("StackTrace: ", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping(value = "/listarFac")
    public ResponseEntity<ResponseListFacDes> listarBbFac(
            @RequestParam(value = "fecha", required = false) String fecha,
            @RequestParam(value = "fecha_hasta", required = false) String fechafin,
            @RequestParam(value = "pefijo", required = false) String prefijo,
            @RequestParam(value = "consecutivo", required = false) String consecutivo,
            @RequestParam(value = "proveedor", required = false) String proveedor) {

        try {
            CompletableFuture<ResponseListFacDes> futureResponse;
            if (fecha != null && !fecha.isEmpty()) {
                try {
                    LocalDateTime fechaFinal;
                    LocalDateTime fechaInicial;

                    try {
                        fechaInicial = LocalDateTime.parse(fecha, DATE_FORMAT_HORA);
                    } catch (DateTimeParseException e) {
                        fechaInicial = LocalDate.parse(fecha, DATE_FORMAT).atStartOfDay();
                    }

                    if (fechafin != null && !fechafin.isEmpty()) {
                        try {
                            fechaFinal = LocalDateTime.parse(fechafin, DATE_FORMAT_HORA);
                        } catch (DateTimeParseException e) {
                            fechaFinal = LocalDate.parse(fechafin, DATE_FORMAT).atStartOfDay();
                        }
                    } else {
                        fechaFinal = fechaInicial;
                    }

                    String fechaInicialStr = (fecha.contains(":"))
                            ? fechaInicial.format(DATE_FORMAT_HORA)
                            : fechaInicial.format(DATE_FORMAT);

                    String fechaFinalStr = (fechafin != null && fechafin.contains(":"))
                            ? fechaFinal.format(DATE_FORMAT_HORA)
                            : fechaFinal.format(DATE_FORMAT);

                    log.info("Consultando facturas para el rango: {} - {}",
                            fechaInicialStr,
                            fechaFinalStr);

                    futureResponse = facOpenservice.traerOpenAsync(
                            fechaInicialStr,
                            fechaFinalStr,
                            prefijo,
                            consecutivo,
                            proveedor);
                } catch (DateTimeParseException e) {
                    log.error("Formato de fecha inválido: {}", fecha);
                    return ResponseEntity.badRequest().body(new ResponseListFacDes());
                }
            } else {
                LocalDate today = LocalDate.now();
                LocalDate yesterday = today.minusDays(1);

                log.info("Consultando facturas para el rango por defecto: {} - {}",
                        yesterday.format(DATE_FORMAT),
                        today.format(DATE_FORMAT));

                futureResponse = facOpenservice.traerOpenAsync(
                        today.format(DATE_FORMAT),
                        today.format(DATE_FORMAT),
                        prefijo,
                        consecutivo,
                        proveedor);
            }

            ResponseListFacDes response = futureResponse.get(60, TimeUnit.SECONDS);
            log.info("Cantidad total de facturas recuperadas: {}", response.getData().size());
            return ResponseEntity.ok(response);

        } catch (TimeoutException e) {
            log.error("Timeout al actualizar facturas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .body(new ResponseListFacDes());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Operación interrumpida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseListFacDes());
        } catch (Exception e) {
            log.error("Error al actualizar facturas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseListFacDes());
        }
    }

    @GetMapping(value = "/descargarFac")
    @Operation(summary = "Descargar factura en openEtl", description = "Permite descargar la factura seleccionada de open")
    public ResponseFacDes descargarFacturacion(@RequestParam("cufe") String cufe, @RequestParam("fecha") String fecha) {
        log.info("Iniciando descargarFac servicio - {}", LocalDateTime.now().format(FORMATTER));

        return facOpenservice.descargarFacturacion(cufe, fecha);
    }

    @PutMapping(value = "/marcarFac")
    @Operation(summary = "Marcar factura en openEtl", description = "Permite marcar la factura seleccionada de open")
    public ResponseEntity<ResposneMarca> marcarFacturacion(@RequestParam("cufe") String cufe) {
        ResposneMarca resp = facOpenservice.marcarFacturacion(cufe);
        if (resp.getStatus().equals("200")) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(400).body(resp);
        }
    }

    @Operation(summary = "Este método obtiene la factura emitida solo si esta no se encuentra clasificada", description = "Este método obtiene la factura emitida solo si esta no se encuentra clasificada", responses = {
            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseConsultaDocumentoDto.class))))
    })
    @GetMapping("/consultaDocumento")
    public ResponseEntity<ResponseConsultaDocumentoDto> consultaDocumento(
            @RequestParam(value = "ofe_identificacion", required = false) Integer ofeIdentificacion,
            @RequestParam(value = "proveedor", required = false) String proveedor,
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "prefijo", required = false) String prefijo,
            @RequestParam(value = "consecutivo", required = false) Integer consecutivo,

            @RequestParam(value = "cufe", required = false) String cufe,
            @RequestParam(value = "fecha", required = false) String fecha) throws FacturacionException {
        boolean consultaPorCufeYFecha = (cufe != null && fecha != null);
        boolean consultaPorDatosGenerales = (proveedor != null && prefijo != null && consecutivo != null
                && ofeIdentificacion != null && tipo != null);

        if (consultaPorCufeYFecha && consultaPorDatosGenerales) {
            throw new IllegalArgumentException(
                    "Debe proporcionar solo una combinación: CUFE y fecha, o proveedor, prefijo, tipo, consecutivo y ofe_identificacion.");
        }

        if (!consultaPorCufeYFecha && !consultaPorDatosGenerales) {
            throw new IllegalArgumentException(
                    "Debe proporcionar una combinación: CUFE y fecha, o proveedor, prefijo, tipo, consecutivo y ofe_identificacion.");
        }

        ResponseConsultaDocumentoDto consultaDocumento = facOpenservice.consultaDocumento(ofeIdentificacion, prefijo,
                consecutivo, tipo, proveedor, cufe, fecha);

        return ResponseEntity.ok(consultaDocumento);
    }

    @Operation(summary = "Este método permite crear una radiación sobre el gestor documental SGDEA agrupada por clasificaciones", description = "Este método permite crear una radiación sobre el gestor documental SGDEA agrupada por clasificaciones", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseClasifDocDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/clasificacionDeDocumento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseClasifDocDto> clasificacionDocumento(
            @Valid @ModelAttribute ClasificacionDocumentoRequestDto requestDto) throws FacturacionException {
        return ResponseEntity.ok(facOpenservice.clasificacionDocumento(requestDto));
    }

    @GetMapping(value = "/consultar/")
    public ResponseEntity<ResponseListFacDes> GetFacturaProvedor(@RequestParam(value = "proveedor", required = true) String proveedor){
       List<FacturacionDescargaDto> data = facOpenservice.listarFacturasProvedor(proveedor);
        ResponseListFacDes response = new ResponseListFacDes();
        response.setStatus("202");
        response.setTotal(data.size());
        response.setData(data);
        return ResponseEntity.ok(response);
    }


}
