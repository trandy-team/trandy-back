package org.trandy.trandy_server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;
    private int statusCode;

    public static <T> ResponseDto<T> success(T body){

        return new ResponseDto<>(body,200);
    }
}
