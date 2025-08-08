package com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDocEmisionOpen {
    private DocEmisionOpen documentos;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DocEmisionOpen {
        @JsonProperty("FC")
        private List<DocumentoRequestDTO> FC;

        @JsonProperty("NC")
        private List<DocumentoRequestDTO> NC;

        @JsonProperty("ND")
        private List<DocumentoRequestDTO> ND;
    }
}
