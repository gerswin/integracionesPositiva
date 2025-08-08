package com.prolinktic.sgdea.service.radicado;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prolinktic.sgdea.dtos.radicado.AgregarAnexos;
import com.prolinktic.sgdea.dtos.radicado.AnexoResponse;
import com.prolinktic.sgdea.dtos.radicado.RadicadoEntradaDTO;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RadicadoService {

  //modificar este
  Object createEntrada(RadicadoEntradaDTO radicadoEntradaDTO, MultipartFile[] anexos, MultipartFile file) throws Exception;
  AnexoResponse agregarAnexos(AgregarAnexos agregarAnexos, MultipartFile[] anexos) throws JsonProcessingException;

  Object obtenerNodeAlfrescoDeSeccionSubSeccion(String codigo) throws JSONException;

}
