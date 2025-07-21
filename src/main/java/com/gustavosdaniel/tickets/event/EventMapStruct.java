package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapStruct {

    ListEventTicketTypeResponseDTO toListEventTicketTypeResponseDTO(TicketType ticketType);
    ListEventResponseDTO toListEventResponseDTO(Event event);
}
