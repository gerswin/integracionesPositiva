package com.prolinktic.sgdea.service.instrumento;

import java.util.List;
import java.util.Map;

public interface InstrumentoService {

    Map<String, Object> consultarInstrumentos(String codigo, String codigoSerieSubserie);
}
