package com.gustavosdaniel.tickets.ticketValidation;

import com.gustavosdaniel.tickets.ticket.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDTO  toTicketValidationResponseDTO(TicketValidation ticketValidation);
}
