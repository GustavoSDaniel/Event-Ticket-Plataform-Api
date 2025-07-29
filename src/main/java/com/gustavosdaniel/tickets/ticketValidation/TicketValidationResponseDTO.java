package com.gustavosdaniel.tickets.ticketValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDTO {

    private UUID ticketId;
    private TicketValidationStatusEnum status;

}
