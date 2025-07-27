package com.gustavosdaniel.tickets.ticket;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);

    Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);
}
