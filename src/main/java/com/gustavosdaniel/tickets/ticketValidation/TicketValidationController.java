package com.gustavosdaniel.tickets.ticketValidation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1/ticket-validations")
@RestController
@AllArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @GetMapping
    public ResponseEntity<TicketValidationResponseDTO> validationTicket(
            @RequestBody TicketValidationRequestDTO ticketValidationRequestDTO
    ){

        TicketValidationMethodEnum method = ticketValidationRequestDTO.getMethod();
        TicketValidation ticketValidation;

        if (TicketValidationMethodEnum.MANUAL.equals(method)) {
             ticketValidation = ticketValidationService
                    .validateTicketByManually(ticketValidationRequestDTO.getId());
        }else {
             ticketValidation = ticketValidationService
                    .validateTicketByQrCode(ticketValidationRequestDTO.getId());
        }

        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDTO(ticketValidation));
    }
}
