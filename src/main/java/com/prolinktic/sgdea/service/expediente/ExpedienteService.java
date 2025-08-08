package com.prolinktic.sgdea.service.expediente;

import com.prolinktic.sgdea.dtos.expediente.ExpedienteCerrarDto;
import com.prolinktic.sgdea.dtos.expediente.ResponseExpedienteCerrarDto;

public interface ExpedienteService {
    ResponseExpedienteCerrarDto cerrarExpedientes(ExpedienteCerrarDto request);
}
