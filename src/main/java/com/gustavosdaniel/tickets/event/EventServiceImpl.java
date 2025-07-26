package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.InvalidPriceException;
import com.gustavosdaniel.tickets.ticketType.TicketType;
import com.gustavosdaniel.tickets.ticketType.TicketTypeNotFoundException;
import com.gustavosdaniel.tickets.ticketType.UpdateTicketTypeRequest;
import com.gustavosdaniel.tickets.user.User;
import com.gustavosdaniel.tickets.user.UserNotFoundException;
import com.gustavosdaniel.tickets.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizedId, CreateEventRequest CreateEventRequest) {
        User organized = userRepository.findById(organizedId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizedId )));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = CreateEventRequest.getTicketsTypes().stream()
                .map(requestTicketType -> {

                    if (requestTicketType.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                        throw new InvalidPriceException(
                                "O preço do ingresso "
                                        + requestTicketType.getName()
                                        + " não pode ser menor que zero."
                        );
                    }
                    return TicketType.builder()
                            .name(requestTicketType.getName())
                            .price(requestTicketType.getPrice())
                            .description(requestTicketType.getDescription())
                            .totalAvailable(requestTicketType.getTotalAvailable())
                            .event(eventToCreate)
                            .build();
                })
                .collect(Collectors.toList());


        eventToCreate.setName(eventToCreate.getName());
        eventToCreate.setStartTime(eventToCreate.getStartTime());
        eventToCreate.setEndTime(eventToCreate.getEndTime());
        eventToCreate.setVenue(eventToCreate.getVenue());
        eventToCreate.setSalesStart(eventToCreate.getSalesStart());
        eventToCreate.setSalesEnd(eventToCreate.getSalesEnd());
        eventToCreate.setStatus(eventToCreate.getStatus());
        eventToCreate.setOrganizer(organized);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOganizer(UUID organizedId, Pageable pageable) {

        userRepository.findById(organizedId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizedId )));

        if (eventRepository.findByOrganizerId(organizedId, pageable).isEmpty()) {

            throw new EventNotFoundException("O organizador não tem eventos em seu nome" );
        }

        return eventRepository.findByOrganizerId(organizedId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizedId, UUID id) { // Optional = Melhor prática para indicar que o resultado pode não existir

        return eventRepository.findByIdAndOrganizerId(id, organizedId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizedId, UUID id, UpdateEventRequest updateEventRequest) {

        if (null == updateEventRequest.getId() ) {
            throw new EventUpdateNotFoundException("Event ID cannot be null");
        }

        if (!id.equals(updateEventRequest.getId())) {
            throw new EventUpdateNotFoundException("Cannot update tha ID of an event");
        }

        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(id, organizedId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with ID '%s' does not exist", id))
                );

        existingEvent.setName(updateEventRequest.getName());
        existingEvent.setStartTime(updateEventRequest.getStart());
        existingEvent.setEndTime(updateEventRequest.getEnd());
        existingEvent.setVenue(updateEventRequest.getVenue());
        existingEvent.setSalesStart(updateEventRequest.getSalesStart());
        existingEvent.setSalesEnd(updateEventRequest.getSalesEnd());
        existingEvent.setStatus(updateEventRequest.getStatus());

        Set<UUID> requestTikectType = updateEventRequest.getTicketTypes() // Extrai os IDs  enviados para atualização Isso será usado para comparar com os tipos já existentes.
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTycketType ->
                !requestTikectType.contains(existingTycketType.getId()) // Remove do evento todos os tipos de ingresso que não estão na lista enviada pela requisição — ou seja, implementa uma sincronização.
        );

        Map<UUID, TicketType> existingTikectTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        for (UpdateTicketTypeRequest ticketType : updateEventRequest.getTicketTypes()) {

            if (null == ticketType.getId()) { //  cria novo
                // create

                if (ticketType.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvalidPriceException(
                            "O preço do ingresso "
                                    + updateEventRequest.getName()
                                    + " não pode ser menor que zero."
                    );
                }

                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
                
            } else if (existingTikectTypesIndex.containsKey(ticketType.getId())) { // atualiza
                //update

                TicketType existingTicketType = existingTikectTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            }else  {
                throw new TicketTypeNotFoundException(String.format(  // lança exceção
                        "Ticket type with ID '%s' dos not exist", ticketType.getId())
                );
            }
        }

        return eventRepository.save(existingEvent);

    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizedId, UUID id) {

        getEventForOrganizer(organizedId, id).ifPresent(eventRepository::delete)    ;

    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
       return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
       return eventRepository.searchEvents(query, EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Optional<Event> getEventPublished(UUID organizedId) {
        return eventRepository.findByIdAndStatus(organizedId, EventStatusEnum.PUBLISHED);
    }


}
