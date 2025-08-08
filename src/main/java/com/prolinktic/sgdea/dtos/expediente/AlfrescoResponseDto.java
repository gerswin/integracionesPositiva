package com.prolinktic.sgdea.dtos.expediente;

import com.fasterxml.jackson.annotation.*;
import com.prolinktic.sgdea.dtos.Entry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "entry"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlfrescoResponseDto {

    @JsonProperty("entry")
    public Entry entry;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
