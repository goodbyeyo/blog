package com.blog.controller;

import com.blog.exception.BlogException;
import com.blog.exception.InvalidRequest;
import com.blog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(Exception.class)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody   // ResponseBody 어노테이션이 있어야 에러메세지 리턴가능
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        // ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다");
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(BlogException.class )
    public ResponseEntity<ErrorResponse> postNotFound(BlogException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(statusCode).body(body);
    }

    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorResponse> blogException(BlogException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
//        if (e instanceof InvalidRequest) {
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//            body.addValidation(fieldName, message);
//        }
        return ResponseEntity.status(statusCode).body(body);
    }

    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody   // ResponseBody 어노테이션이 있어야 에러메세지 리턴가능
    public Map<String, String> invalidRequestHandler(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();

        Map<String, String> response = new HashMap<>();
        response.put(field, message);
        return response;
    }
    */

   /* 1)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e) {
     // MethodArgumentNotValidException
     // e.getField()
        log.info("exceptionHandler error", e);
    }
    */
}
