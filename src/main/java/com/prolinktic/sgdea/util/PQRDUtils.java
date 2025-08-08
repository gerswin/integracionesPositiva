package com.prolinktic.sgdea.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.prolinktic.sgdea.common.PQRDConstants;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PQRDUtils {
    @Getter
    private static final ObjectMapper JSON_MAPPER;

    static {
        JSON_MAPPER = new ObjectMapper();
        JSON_MAPPER.configure(SerializationFeature.INDENT_OUTPUT,false);
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static String protectFields(String jsonTextI, String... protectFields) {
        String jsonText = jsonTextI;
        for (String protectField : protectFields) {
            String regex = PQRDConstants.QUOTES + protectField + PQRDConstants.REGEX_REPLACE_JSON_VALUE;
            Matcher m = Pattern.compile(regex).matcher(jsonText);
            while (m.find()) {
                String match = m.group();
                String replace = m.group().replace(protectField, PQRDConstants.EMPTY_STRING).replace(PQRDConstants.QUOTES, PQRDConstants.EMPTY_STRING).replace(PQRDConstants.TWO_DOTS, PQRDConstants.EMPTY_STRING);
                String placeHolder = replace.replaceAll(PQRDConstants.REGEX_ALL, PQRDConstants.ASTERISK);
                String ret = match.replace(replace, placeHolder);
                jsonText = jsonText.replace(match, ret);
            }
        }
        return jsonText;
    }

    public static Map<String, Object> convertirDTOaMap(Object dto) throws IllegalAccessException {
        Map<String, Object> mapa = new HashMap<>();

        Field[] campos = dto.getClass().getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            Object valor = campo.get(dto);
            if (valor != null) {
                mapa.put(campo.getName(), valor.toString());
            }

        }

        return mapa;
    }

    public static Map<String, Object> convertDTOToMap(Object dto) {
        Map<String, Object> result = new HashMap<>();

        Class<?> dtoClass = dto.getClass();

        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            Object value = null;
            try {
                value = field.get(dto);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            result.put(fieldName, value);

        }

        return result;
    }

    public static MultiValueMap<String, Object> convertDTOToMultiValueMap(Object dto) {
        MultiValueMap<String, Object> result = new LinkedMultiValueMap<>();

        Class<?> dtoClass = dto.getClass();

        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(dto);
                result.add(fieldName, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}