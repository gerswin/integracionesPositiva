package com.prolinktic.sgdea.service.openEmision;


import com.prolinktic.sgdea.dtos.OpenEtlDto.DatosGeneralesDocDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.PdfNotificacionDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.XmlUblDianDto;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocEmisionOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.infrastructura.gateway.OpenEtlRestTemplateImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenEmisionServiceImpl implements OpenEmisionService {

    @Autowired
    private OpenEtlRestTemplateImpl open;

    @Override
    public ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocEmisionOpen documento) throws Exception {
        log.debug("Empezando proceso crearDocumentoEnEmision");
        try {
            return open.crearDocumentoEnEmision(documento);
        } catch (Exception e) {
            log.error("Error al registrar documentos en Open", e);
            throw new Exception("Error al registrar documentos en Open", e);
        }
    }

    @Override
    public PdfNotificacionDto obtenerPdfEmision(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) throws Exception {
        log.debug("Empezando proceso obtenerPdfEmision");
        try {
            return open.obtenerPdf(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
        } catch (Exception e) {
            log.error("Error al obtener pdf en Open", e);
            throw new Exception("Error al obtener pdf en Open", e);
        }
    }

    @Override
    public XmlUblDianDto obtenerXmlEmision(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) throws Exception {
        log.debug("Empezando proceso obtenerPdfEmision");
        try {
            return open.obtieneXmlUblDIAN(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
        } catch (Exception e) {
            log.error("Error al obtener pdf en Open", e);
            throw new Exception("Error al obtener pdf en Open", e);
        }
    }

    @Override
    public String obtenerAttachedDocument(String tipoDocumento, String prefijo, String consecutivo, Integer ofeIdentificacion) throws Exception {
        log.debug("Empezando proceso obtenerAttachedDocument");
        try {
            return open.obtenerAttachedDocumentDocumentoEnEmision(tipoDocumento, prefijo, consecutivo, ofeIdentificacion);
        } catch (Exception e) {
            log.error("Error al obtener pdf en Open", e);
            throw new Exception("Error al obtener pdf en Open", e);
        }
    }

    @Override
    public DatosGeneralesDocDto consultarDatosGenerales(Integer ofeIdentificacion, String prefijo, Integer consecutivo) throws Exception {
        log.debug("Empezando proceso consultarDatosGenerales");
        try {
            return open.consultarDatosGenerales(ofeIdentificacion, prefijo, consecutivo);
        } catch (Exception e) {
            log.error("Error al obtener pdf en Open", e);
            throw new Exception("Error al obtener pdf en Open", e);
        }
    }
}
