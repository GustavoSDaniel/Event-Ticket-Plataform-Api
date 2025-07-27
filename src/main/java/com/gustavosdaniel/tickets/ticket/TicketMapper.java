package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.ticketType.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketResponseTicketTypeDTO toListTicketResponseTicketTypeDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO( Ticket ticket);


}
