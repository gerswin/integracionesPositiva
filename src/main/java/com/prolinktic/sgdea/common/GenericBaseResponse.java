package com.prolinktic.sgdea.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericBaseResponse<T>  extends IModel{

    private Integer code;
    private String message;
    private HttpStatus httpStatus;
    private T data;
    private List<T> dataList;

    public static <T extends GenericBaseResponse > GenericBaseResponse responseSuccess(T data) {
        GenericBaseResponse<T> responseSuccess = new GenericBaseResponse();
        responseSuccess.setCode(HttpStatus.OK.value());
        responseSuccess.setMessage(HttpStatus.OK.toString());
        responseSuccess.setHttpStatus(HttpStatus.OK);
        responseSuccess.setData(data);
        return responseSuccess;
    }

    public static <T extends GenericBaseResponse> GenericBaseResponse responseSuccessList(List<T> data) {
        GenericBaseResponse<T> responseSuccess = new GenericBaseResponse();
        responseSuccess.setCode(HttpStatus.OK.value());
        responseSuccess.setMessage(HttpStatus.OK.toString());
        responseSuccess.setHttpStatus(HttpStatus.OK);
        responseSuccess.setDataList(data);
        return responseSuccess;
    }

}