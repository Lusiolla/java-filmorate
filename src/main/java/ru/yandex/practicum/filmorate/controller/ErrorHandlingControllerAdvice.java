package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleThereIsNoValidationException() {
        Exception e = new Exception("The object is not valid");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class Exception {
        private String message;
    }





}
