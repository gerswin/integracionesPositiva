package com.prolinktic.sgdea.dao.Expediente;

import com.prolinktic.sgdea.model.Expediente.Expediente;

public interface ExpedientesDao {
    Boolean actualizarExpediente(Expediente expediente);
    Expediente buscarId(int id);
    Expediente buscarNodeId(String id);
}
