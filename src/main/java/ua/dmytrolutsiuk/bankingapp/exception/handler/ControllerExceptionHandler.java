package ua.dmytrolutsiuk.bankingapp.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.dmytrolutsiuk.bankingapp.exception.EntityNotFoundException;
import ua.dmytrolutsiuk.bankingapp.exception.NotEnoughFundsException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleException(EntityNotFoundException e) {
        return new ErrorResponseImpl(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public ErrorResponse handleException(NotEnoughFundsException e) {
        return new ErrorResponseImpl(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
