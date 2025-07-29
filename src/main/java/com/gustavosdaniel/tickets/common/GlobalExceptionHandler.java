package com.gustavosdaniel.tickets.common;

import com.gustavosdaniel.tickets.event.EventNotFoundException;
import com.gustavosdaniel.tickets.event.EventUpdateNotFoundException;
import com.gustavosdaniel.tickets.qrcode.QrCodeGenerationException;
import com.gustavosdaniel.tickets.qrcode.QrCodeNotFoundException;
import com.gustavosdaniel.tickets.ticket.TicketsNotFoundException;
import com.gustavosdaniel.tickets.ticket.TicketsSoldOutFoundException;
import com.gustavosdaniel.tickets.ticketType.InvalidPriceException;
import com.gustavosdaniel.tickets.ticketType.TicketTypeNotFoundException;
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

    @ExceptionHandler(TicketsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTicketsNotFoundException(TicketsNotFoundException e) {

        log.error("Caught TicketsNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Ticket not found");

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketsSoldOutFoundException.class)
    public ResponseEntity<ErrorDTO> handleTicketsSoldOutFoundException(TicketsSoldOutFoundException e) {

        log.error("Caught TicketsSoldOutFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Tickets are Sold Out for this ticket type");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleQrCodeNotFoundException(QrCodeNotFoundException e) {

        log.error("Caught QrCodeNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("QrCode not found");

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<ErrorDTO> handleInvaQrCodeGenerationException(QrCodeGenerationException e) {

        log.error("Caught QrCodeGenerationException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Unable to generate QR Code");

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ErrorDTO> handleInvalidPriceException(InvalidPriceException e) {

        log.error("Caught InvalidPriceException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Price not valid");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventUpdateNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEventUpdateNotFoundException(EventUpdateNotFoundException e) {

        log.error("Caught EventUpdateNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Unable to update event");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTicketTypeNotFoundException(TicketTypeNotFoundException e) {

        log.error("Caught TicketTypeNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Ticket Type not found");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEventNotFoundException(EventNotFoundException e) {

        log.error("Caught EventNotFoundException", e);

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError("Event not found");

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

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
