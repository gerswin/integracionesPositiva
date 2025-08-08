package com.prolinktic.sgdea.dao.FacturacionDescarga;

import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;

import java.util.List;

public interface FacturacionDescargaDao {
    List<FacturacionDescarga> findByCufe(String cufe);

    List<FacturacionDescarga> findByProveedor(String proveedor);

    List<FacturacionDescarga> findByPrefijoAndConsecutivo(String prefijo, String consecutivo);

    List<FacturacionDescarga> findByConsecutivo(String consecutivo);

    List<FacturacionDescarga> findByPrefijo(String prefijo);

    Boolean existsByCufe(String cufe);

    FacturacionDescarga findByCufeAndProveedorAndPrefijoAndConsecutivo(String cufe, String proveedor, String prefijo, String consecutivo);
}
