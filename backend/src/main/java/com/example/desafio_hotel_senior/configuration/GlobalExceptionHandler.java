package com.example.desafio_hotel_senior.configuration;

import com.example.desafio_hotel_senior.Application;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        LoggerFactory.getLogger(Application.class).error(message, e);
        return new ResponseEntity<>(message, BAD_REQUEST);
    }


    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    ResponseEntity<String> handleException(org.hibernate.exception.ConstraintViolationException e) {
        Throwable error = e.getCause().getCause();
        String message = error.getMessage();
        LoggerFactory.getLogger(Application.class).error(message, error);
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> handleException(DataIntegrityViolationException e) {
        String message = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
        LoggerFactory.getLogger(Application.class).error(message, e);
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        String message = e.getAllErrors().stream()//
                .map(ObjectError::getDefaultMessage)//
                .collect(Collectors.joining("\n"));
        LoggerFactory.getLogger(Application.class).error(message, e);
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity<String> handleException(TransactionSystemException e) {
        Throwable error = e.getCause().getCause();
        String message = error.getMessage();
        LoggerFactory.getLogger(Application.class).error(message, error);
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<String> handleException(ResponseStatusException e) {
        String reason = e.getReason();
        if (StringUtils.isNotBlank(reason) && reason.contains("\"")) {
            reason = StringUtils.substringBetween(reason, "\"");
        }
        if (StringUtils.isBlank(reason)) {
            if (e.getStatusCode().value() == 404) {
                reason = "Entidade não encontrada";
            } else if (e.getStatusCode().is5xxServerError()) {
                reason = "Erro interno do servidor";
            }
        }
        LoggerFactory.getLogger(Application.class).error(reason, e);
        return new ResponseEntity<>(reason, e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e) {
        LoggerFactory.getLogger(Application.class).error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

}