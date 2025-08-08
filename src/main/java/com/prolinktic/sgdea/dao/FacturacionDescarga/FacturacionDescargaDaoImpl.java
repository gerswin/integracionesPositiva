package com.prolinktic.sgdea.dao.FacturacionDescarga;

import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FacturacionDescargaDaoImpl implements FacturacionDescargaDao{
    @Autowired
    private FacturacionDescargaJpaSpring jpaSpring;

    @Override
    public List<FacturacionDescarga> findByCufe(String cufe) {
        return jpaSpring.findByCufe(cufe);
    }

    @Override
    public List<FacturacionDescarga> findByProveedor(String proveedor) {
        return jpaSpring.findByProveedorAndMarcaFalse(proveedor);
    }

    @Override
    public List<FacturacionDescarga> findByPrefijoAndConsecutivo(String prefijo, String consecutivo) {
        return jpaSpring.findByPrefijoAndConsecutivo(prefijo,consecutivo);
    }

    @Override
    public List<FacturacionDescarga> findByConsecutivo(String consecutivo) {
        return jpaSpring.findByConsecutivo(consecutivo);
    }

    @Override
    public List<FacturacionDescarga> findByPrefijo(String prefijo) {
        return jpaSpring.findByPrefijo(prefijo);
    }

    @Override
    public Boolean existsByCufe(String cufe) {
        return jpaSpring.existsByCufe(cufe);
    }

    @Override
    public FacturacionDescarga findByCufeAndProveedorAndPrefijoAndConsecutivo(String cufe, String proveedor, String prefijo, String consecutivo) {
        return jpaSpring.findByCufeAndProveedorAndPrefijoAndConsecutivo(cufe, proveedor, prefijo, consecutivo);
    }
}
