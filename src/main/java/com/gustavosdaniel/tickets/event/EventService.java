package com.gustavosdaniel.tickets.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


public interface EventService {

    Event createEvent(UUID organizedId,  CreateEventRequest CreateEventRequest);

    Page<Event> listEventsForOganizer(UUID organizedId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizedId, UUID id);

    Event updateEventForOrganizer(UUID organizedId, UUID id, UpdateEventRequest UpdateEventRequest);

    void deleteEventForOrganizer(UUID organizedId, UUID id);

    Page<Event> listPublishedEvents(Pageable pageable);

    Page<Event> searchPublishedEvents(String query, Pageable pageable);




}
