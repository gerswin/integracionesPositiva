package com.prolinktic.sgdea.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.prolinktic.sgdea.util.PQRDUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@Slf4j
public abstract class IModel implements Serializable {

    @Override
    public String toString() {
        return toJson();
    }

    /**
     * This method return string to show in logs with sensible fields protected
     *
     * @param protectFields fields to protect
     * @return protected to String of object in json format
     */
    public String toJson(String... protectFields) {
        try {
            return PQRDUtils.protectFields(toJsonString(), protectFields);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return PQRDConstants.EMPTY_STRING;
        }
    }

    private String toJsonString() throws JsonProcessingException {
        return PQRDUtils.getJSON_MAPPER().writeValueAsString(this);
    }

    /**
     * This method return string to show in logs with sensible fields protected
     *
     * @return protected to String of object in xml/json format
     */
    public String protectedToString() {
        return toJson("refresh_token", "access_token");
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefaultModel extends IModel {

    }
}