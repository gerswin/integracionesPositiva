package com.prolinktic.sgdea.dao.tipologiaDocumental;



import com.prolinktic.sgdea.model.tipologiaDocumental.TipologiaDocumental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipologiaDocumentalDao extends JpaRepository<TipologiaDocumental, Long> {
}

