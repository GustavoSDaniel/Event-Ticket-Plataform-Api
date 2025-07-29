package com.gustavosdaniel.tickets.ticketValidation;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidation validateTicketByQrCode(UUID qrCodeId);

    TicketValidation validateTicketByManually(UUID ticketId);
}
