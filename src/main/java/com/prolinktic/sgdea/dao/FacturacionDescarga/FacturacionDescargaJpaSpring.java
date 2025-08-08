package com.prolinktic.sgdea.dao.FacturacionDescarga;

import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FacturacionDescargaJpaSpring extends JpaRepository<FacturacionDescarga, Integer>, JpaSpecificationExecutor<FacturacionDescarga> {
    List<FacturacionDescarga> findByCufe(String cufe);

    List<FacturacionDescarga> findByPrefijoAndConsecutivo(String prefijo, String consecutivo);

    List<FacturacionDescarga> findByConsecutivo(String consecutivo);

    List<FacturacionDescarga> findByPrefijo(String prefijo);

    Boolean existsByCufe(String cufe);

    List<FacturacionDescarga> findByProveedorAndMarcaFalse(String proveedor);


    FacturacionDescarga findByCufeAndProveedorAndPrefijoAndConsecutivo(String cufe, String proveedor, String prefijo, String consecutivo);
}
