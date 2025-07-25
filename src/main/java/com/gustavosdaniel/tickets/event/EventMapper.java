package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeRequest;
import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeRequestDTO;
import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeResponseDTO;
import com.gustavosdaniel.tickets.ticketType.TicketType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapper {

    public CreateEventRequest fromCreateRequestDTO(CreateEventRequestDTO createEventRequestDTO) {

        return CreateEventRequest.builder()
                .name(createEventRequestDTO.getName())
                .start(createEventRequestDTO.getStart())
                .end(createEventRequestDTO.getEnd())
                .venue(createEventRequestDTO.getVenue())
                .salesStart(createEventRequestDTO.getSalesStart())
                .salesEnd(createEventRequestDTO.getSalesEnd())
                .status(createEventRequestDTO.getStatus())
                .ticketsTypes(mapTicketTypes(createEventRequestDTO.getTicketsTypes()))
                .build();
    }


    private CreateTicketTypeRequest mapTicketTypes(CreateTicketTypeRequestDTO createTicketTypeRequestDTO) {
        return CreateTicketTypeRequest.builder()
                .name(createTicketTypeRequestDTO.getName())
                .description(createTicketTypeRequestDTO.getDescription())
                .price(createTicketTypeRequestDTO.getPrice())
                .totalAvailable(createTicketTypeRequestDTO.getTotalAvailable())
                .build();
    }


    // Transforma uma lista de DTOs em uma lista de entidades TicketType
    private List<CreateTicketTypeRequest> mapTicketTypes(List<CreateTicketTypeRequestDTO> ticketsTypesRequestDTO) {
        return ticketsTypesRequestDTO.stream()
                .map(this::mapTicketTypes)
                .collect(Collectors.toList());
    }

    public CreateEventResponseDTO toEvent(Event event) {
        return CreateEventResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .start(event.getStartTime())
                .end(event.getEndTime())
                .venue(event.getVenue())
                .salesStart(event.getSalesStart())
                .salesEnd(event.getSalesEnd())
                .status(event.getStatus())
                .createTicketTypeResponseDTO(mapTicketTypesToResponseDTOs(event.getTicketTypes()))
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    private CreateTicketTypeResponseDTO toTicketTypeResponseDTO(TicketType ticketType) {
        return CreateTicketTypeResponseDTO.builder()
                .id(ticketType.getId())
                .name(ticketType.getName())
                .description(ticketType.getDescription())
                .price(ticketType.getPrice())
                .totalAvailable(ticketType.getTotalAvailable())
                .build();

    }

    private List<CreateTicketTypeResponseDTO> mapTicketTypesToResponseDTOs(List<TicketType> ticketTypes) {
        return ticketTypes.stream()
                .map(this::toTicketTypeResponseDTO)
                .collect(Collectors.toList());
    }
}
