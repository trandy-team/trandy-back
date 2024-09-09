package org.trandy.trandy_server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
        공통 예외처리 (Enum에 지정한 에러 코드, 메시지 반환
        별도의 프로세스로 처리되어야할 Case에 대해서는 @ExceptionHandler를 추가하여 대응한다.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException ex){
        log.error("=====================================================================");
        log.error("{} : {}", ex.getExceptionStatus().getExceptionName(),
                ex.getExceptionStatus().getErrorMessage());
        log.error("=====================================================================");

        return new ResponseEntity<>(ExceptionDto.builder()
                .exceptionName(ex.getExceptionStatus().getExceptionName())
                .message(ex.getExceptionStatus().getErrorMessage())
                .statusCode(ex.getExceptionStatus().getStatusCode().value())
                .build(),
                HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode().value()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidException(MethodArgumentNotValidException ex){
        BindingResult rs = ex.getBindingResult();
        List<FieldError> fieldErrors = rs.getFieldErrors();

        StringBuilder errorMessages = new StringBuilder();

        for (int i=0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);

            errorMessages
                    .append("Parameter : ").append(fieldError.getField())
                    .append(" -> ")
                    .append(fieldError.getDefaultMessage());

        // 마지막 필드일 경우에는 줄바꿈하지 않음
            if (i < fieldErrors.size() - 1) {
                errorMessages.append("\n");
            }
        }

        log.error("=====================================================================");
        log.error("MethodArgumentNotValidException : {}", errorMessages);
        log.error("=====================================================================");

        return new ResponseEntity<>(ExceptionDto.builder()
                .message(errorMessages.toString())
                .statusCode(400)
                .build(),
                HttpStatus.valueOf(400));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDto> handleAuthenticationException(AuthenticationException ex) {
        // 인증 예외 처리 로직 작성
        log.error("=====================================================================");
        log.error("{} : {}", ExceptionStatus.AuthenticationException.getExceptionName(), ex.getMessage());
        log.error("=====================================================================");

        return new ResponseEntity<>(ExceptionDto.builder()
                .exceptionName(ExceptionStatus.AuthenticationException.getExceptionName())
                .message(ex.getMessage())
                .statusCode(ExceptionStatus.AuthenticationException.getStatusCode().value())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException ex) {
//        // 권한 거부 예외 처리 로직 작성
//    }
}
