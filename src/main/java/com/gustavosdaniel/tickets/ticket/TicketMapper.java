package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.ticketType.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketResponseTicketTypeDTO toListTicketResponseTicketTypeDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO( Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.startTime")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.endTime")
    GetTicketResponseDTO toGetTicketResponseDTO(Ticket ticket);


}
