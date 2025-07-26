package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapStruct {

    ListEventTicketTypeResponseDTO toListEventTicketTypeResponseDTO(TicketType ticketType);

    ListEventResponseDTO toListEventResponseDTO(Event event);

    GetEventDetailsResponseDTO toGetEventDetailsResponseDTO(Event event); // Converte o objeto Event para GetEventDetailsResponseDTO

    GetEventTicketTypesResponseDTO toGetEventTicketTypesResponseDTO(TicketType ticketType);

    UpdateTicketTypeRequest toUpdateTicketTypeRequestDTO(UpdateTicketTypeRequestDTO updateTicketTypeRequestDTO);

    UpdateEventRequest toUpdateEventRequestDTO(UpdateEventRequestDTO updateEventRequestDTO);

    UpdateTicketTypeResponseDTO toUpdateTicketTypeResponseDTO(TicketType ticketType);

    UpdateEventResponseDTO toUpdateEventResponseDTO(Event event);

    ListPublishedEventResponseDTO toListPublishedEventResponseDTO(Event event);

    GetPublishedEventDetailsResponseDTO toGetPublishedEventDetailsResponseDTO(Event event);

    GetPublishedEventTicketTypesResponseDTO  toGetPublishedEventTicketTypesResponseDTO(TicketType ticketType);


}
