package com.prolinktic.sgdea.service.expediente;

import com.prolinktic.sgdea.dao.Expediente.ExpedientesDao;
import com.prolinktic.sgdea.dtos.Entry;
import com.prolinktic.sgdea.dtos.expediente.*;
import com.prolinktic.sgdea.model.Alfesco.CreateNode;
import com.prolinktic.sgdea.dtos.radicado.*;
import com.prolinktic.sgdea.infrastructura.gateway.AlfrescoClient;
import com.prolinktic.sgdea.model.Expediente.Expediente;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExpedienteServiceImpl implements ExpedienteService {
    @Autowired
    AlfrescoClient alfrescoClient;
    @Autowired
    ExpedientesDao expedientesDao;
    @Override
    public ResponseExpedienteCerrarDto cerrarExpedientes(ExpedienteCerrarDto request) {
        ResponseExpedienteCerrarDto response = new  ResponseExpedienteCerrarDto();
        // Expediente aux = new Expediente();


        Workbook workbook = new XSSFWorkbook();
        Sheet hoja = workbook.createSheet("Hoja1");
        Row encabezado = hoja.createRow(0);
        encabezado.createCell(0).setCellValue("Número Expediente");
        encabezado.createCell(1).setCellValue("Radicado o Documento");
        encabezado.createCell(2).setCellValue("Detalle");

        int fila = 1;
        int cantCerrados=0;
        int cantNovedades=0;
        String novedad;

        for (String idExpediente : request.getIdExpedientes()) {

            /*AlfrescoResponseDto alfrescoResponse = alfrescoClient.obtenerContentType(aux.getNodeid());
            Entry entries = alfrescoResponse.entry;
            Map<String, Object> properties = (Map<String, Object>) entries.getProperties();
            String estado =  (String) properties.get("ltksg:estado_expediente"); */
            List<String> estadoResponse = expedienteDocumento(idExpediente);
            if ("Finalizado".equals(estadoResponse.get(0))) {
                CreateNode node = CreateNode.builder().build();
                Map<String, Object> propertiess = new HashMap<>();
                propertiess.put("ltksg:estado_expediente", "Cerrado");
                node.setProperties(propertiess);
                alfrescoClient.actualizarNodo(idExpediente, node);
                // aux=expedientesDao.buscarNodeId(idExpediente);
                // aux.setEstado(3);
                // expedientesDao.actualizarExpediente(aux);
                cantCerrados++;
            } else {
                novedad= "No se puede cerrar el expediente #Número de expediente " + idExpediente + estadoResponse.get(0);
                Row filaH = hoja.createRow(fila);
                filaH.createCell(0).setCellValue(idExpediente);
                filaH.createCell(1).setCellValue(estadoResponse.get(1));
                filaH.createCell(2).setCellValue(novedad);
                cantNovedades++;
                fila++;
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] excelBytes = outputStream.toByteArray();
        String base64Excel = Base64.getEncoder().encodeToString(excelBytes);

        response.setTotalRegistros(request.getIdExpedientes().size());
        response.setRegistrosCerrados(cantCerrados);
        response.setRegistrosNovedad(cantNovedades);
        response.setArchivo(base64Excel);

        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(now);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
        String formattedTime = timeFormat.format(now);

        String fileName = "Novedades Cierre Exp " + formattedDate + "-" + formattedTime + "-" + "loggedUser" + ".xlsx";

        response.setNombreArchivo(fileName);
        return response;
    }

    private List<String> expedienteDocumento(String idExpediente) {
        List<String> response = new ArrayList<>();
       /*
        AlfrescoResponseDTO children = alfrescoClient.getAllPlantilla(idExpediente);
        ListDTO list = children.getList();
        List<EntryDTO> entryDTOS = list.getEntries();
        for (EntryDTO entry: entryDTOS) {
            EntryDetailsDTO entryDetailsDTO = entry.getEntry();
            String id = entryDetailsDTO.getId();
            AlfrescoResponseDto alfrescoResponse = alfrescoClient.obtenerContentType(id);
            Entry entries = alfrescoResponse.entry;
            Map<String, Object> properties = (Map<String, Object>) entries.getProperties();
            //String estadoDocumento = properties.sgdposEstadoDocExp;
            String idDocumento = entries.getId();
            if(!"finalizado".equals(estadodocumento)) {
                response.add("ya que tiene asociado el documento " + iddocumento + " con un estado" +  estadodocumento);
                response.add(entries.name);
                return response;
            }
            // validacion radicado
        } */
        response.add("Finalizado");
        return response;
    }
}
