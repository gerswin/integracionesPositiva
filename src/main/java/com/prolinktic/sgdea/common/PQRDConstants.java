package com.prolinktic.sgdea.common;

public class PQRDConstants {
    public static final String SPRING_CONFIG_PREFIX = "spring.application";
    public static final String EMPTY_STRING = "";
    public static final String LBL_START = "INICIANDO TRANSACCIÓN";
    public static final String LBL_REQUEST_TYPE = "[{}] SOLICITUD SERVICIO [{}] PARÁMETROS: {}";
    public static final String LBL_REQUEST_TYPE_WITHOUT_PARAMS = "[{}] SOLICITUD SERVICIO [{}]"; // Service request without params
    public static final String LBL_RESPONSE_SERVICE = "[{}] RESPUESTA SERVICIO [{}]: [{}]";
    public static final String LBL_REQUEST_TYPE_WITHOUT_PARAMS_SCHEDULER = "[{}] INICIANDO JOB [{}]"; // Service request without params
    public static final String LBL_REQUEST_TYPE_SCHEDULER = "[{}] INICIANDO JOB [{}] PARÁMETROS: {}"; // Service request
    public static final String LOG_THIRD_REQUEST = "[{}] [{}] SOLICITUD: URL: [{}], SOLICITUD: [{}]"; // Third request
    public static final String LBL_RESPONSE = "[{}] [{}] RESPUESTA: [\"{}\"]"; // Third or service response
    public static final String LBL_END = "FINAL DE LA TRANSACCIÓN";
    public static final String REGEXP_NUMERIC = "[0-9]+";
    public static final String QUOTES = "\"";
    public static final String REGEX_REPLACE_JSON_VALUE = "\".*?:.*?\".*?\"";
    public static final String REGEX_ALL = ".";
    public static final String ASTERISK = "*";
    public static final String TWO_DOTS = ":";
}