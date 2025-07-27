package com.gustavosdaniel.tickets.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDTO {

    private UUID id;

    private TicketStatusEnum ticketStatus;

    private ListTicketResponseTicketTypeDTO ticketType;




}
