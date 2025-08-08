package com.prolinktic.sgdea.model.versionCCD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "version")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class versionCCD {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idfondo")
    private int id_fondo;
}
