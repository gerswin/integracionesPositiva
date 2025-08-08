package com.prolinktic.sgdea.dtos.OpenEtlDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlUblDianDto {
    @JsonProperty("xml_ubl")
    private String xmlUbl;
}
