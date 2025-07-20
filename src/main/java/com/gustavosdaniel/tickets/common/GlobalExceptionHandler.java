package com.gustavosdaniel.tickets.common;

import com.gustavosdaniel.tickets.user.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException e) {

        log.error("Caught UserNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("User not found");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    //Trata erros de validação de argumentos em métodos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ){

        log.error("Caught ConstraintViolationException", ex);

        ErrorDTO errorDTO = new ErrorDTO();

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        String errorMessage = fieldErrorList.stream()
                .findFirst()
                .map(fieldError ->
                        fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation Error occurred");

        errorDTO.setError(errorMessage);

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    // quando as validações definidas nas anotações como @NotNull, @Size, @Pattern, etc., falham
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException exception) {

        log.error("Caught ConstraintViolationException", exception);

        ErrorDTO errorDTO = new ErrorDTO();

        String errorMessage =  exception.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation ->
                        violation.getPropertyPath() + ": " + violation.getMessage()
                ).orElse("Constraint violation exception");

        errorDTO.setError(errorMessage);

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    //  Captura qualquer exceção não tratada pelos outros handlers
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {

        log.error("Caught exception", exception);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("An unknown error occurred");

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
