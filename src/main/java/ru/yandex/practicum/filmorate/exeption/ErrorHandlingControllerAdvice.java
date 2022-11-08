package ru.yandex.practicum.filmorate.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

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

    @ExceptionHandler(FilmAlreadyExistException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleFilmAlreadyExistException() {
        Exception e = new Exception("The film is already exist");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleUserAlreadyExistException() {
        Exception e = new Exception("The user is already exist");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleFilmNotFoundException() {
        Exception e = new Exception("The film not found");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleUserNotFoundException() {
        Exception e = new Exception("The user not found");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MpaNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleMpaNotFoundException() {
        Exception e = new Exception("The mpa was not found");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleGenreNotFoundException() {
        Exception e = new Exception("The genre was not found");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserFriendNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleUserFriendNotFoundException() {
        Exception e = new Exception("The user's friend was not found");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    protected ResponseEntity<Exception> handleSQLException() {
        Exception e = new Exception("SQLException");
        log.warn(e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class Exception {
        private String message;
    }


}
