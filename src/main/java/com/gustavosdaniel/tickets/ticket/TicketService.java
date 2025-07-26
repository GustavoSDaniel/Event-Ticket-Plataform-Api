package com.gustavosdaniel.tickets.ticket;

import java.util.UUID;

public interface TicketService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
