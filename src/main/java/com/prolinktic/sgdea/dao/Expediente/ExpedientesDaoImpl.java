package com.prolinktic.sgdea.dao.Expediente;

import com.prolinktic.sgdea.model.Expediente.Expediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExpedientesDaoImpl implements ExpedientesDao {
    @Autowired
    private ExpedientesJpaSpring expedientesJpaSpring;
    @Override
    public Boolean actualizarExpediente(Expediente expediente) {
        return expedientesJpaSpring.save(expediente)!=null;
    }

    @Override
    public Expediente buscarId(int id) {
        return expedientesJpaSpring.findById(id).orElse(null);
    }

    @Override
    public Expediente buscarNodeId(String id) {
        return expedientesJpaSpring.findByNodeid(id);
    }
}
