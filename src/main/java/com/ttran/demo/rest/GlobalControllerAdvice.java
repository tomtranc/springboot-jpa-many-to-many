package com.ttran.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

//    @ExceptionHandler(value = { Exception.class })
//    public ResponseEntity<?> handleOtherExceptions(Exception ex, WebRequest request) {
//        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
//        String errorMsg = String.format("Internal Error for request %s: ", requestUri);
//        log.error(errorMsg, ex);
//        return ResponseEntity.internalServerError().body(errorMsg + ex.getMessage());
//    }
//
//    @ExceptionHandler(value = { NoResourceFoundException.class })
//    public ResponseEntity<?> ClientExceptions(Exception ex, WebRequest request) {
//        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
//        String errorMsg = String.format("Client Error for request %s: ", requestUri);
//        log.error(errorMsg, ex);
//        return ResponseEntity.badRequest().body(errorMsg + ex.getMessage());
//    }

}
