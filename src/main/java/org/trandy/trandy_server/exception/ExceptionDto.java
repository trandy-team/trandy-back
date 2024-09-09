package org.trandy.trandy_server.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private String exceptionName;
    private String message;
    private int statusCode;
}
