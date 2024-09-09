package org.trandy.trandy_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    //StackTrace 가지지 않도록 처리.
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
