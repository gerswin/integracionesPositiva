package com.prolinktic.sgdea.service.consultarDocumento;

import com.prolinktic.sgdea.dtos.AlfrescoNodeList;
import com.prolinktic.sgdea.dtos.buscarDocumento.ResponseBuscarDocumento;
import com.prolinktic.sgdea.dtos.radicado.*;
import com.prolinktic.sgdea.exception.exceptions.ControlException;
import org.json.JSONException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DocumentoService {

    //modificar este
    ResponseBuscarDocumento buscarDocumento(String idDocumento) throws Exception;

    ResponseBuscarDocumento buscarDocumento2(String idDocumento, String idDoucmentoFinal, Map<String, Object> newMetadata) throws Exception;


    AlfrescoNodeList obtenerHijosPorNodeId(String nodeId) throws ControlException;

    void updateMetadata(String nodeId, Map<String, Object> newMetadata);


    }

