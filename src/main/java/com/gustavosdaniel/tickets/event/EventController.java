package com.gustavosdaniel.tickets.event;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapStruct eventMapStruct;
    private final EventMapper eventMapper;

    private UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO
    ) {
        CreateEventRequest createRequestDTO = eventMapper.fromCreateRequestDTO(createEventRequestDTO);
        UUID userId = parseUserId(jwt);

        Event createdEvent = eventService.createEvent(userId, createRequestDTO);
        CreateEventResponseDTO createEventResponseDTO = eventMapper.toEvent(createdEvent);
        return new ResponseEntity<>(createEventResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ) {
        UUID userId = parseUserId(jwt);

        Page<Event> events = eventService.listEventsForOganizer(userId, pageable);

        return ResponseEntity.ok(
                events.map(eventMapStruct::toListEventResponseDTO)  // Converte cada Event para ListEventResponseDTO usando MapStruct
        );

    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDTO> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId

    ) {
        UUID userId = parseUserId(jwt);

        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapStruct::toGetEventDetailsResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDTO> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDTO updateEventRequestDTO
    ) {
        UpdateEventRequest updateEventRequest = eventMapStruct.toUpdateEventRequestDTO(updateEventRequestDTO);

        UUID userId = parseUserId(jwt);

        Event updateEvent = eventService.updateEventForOrganizer(
                userId, eventId, updateEventRequest
        );

        UpdateEventResponseDTO updateEventResponseDTO = eventMapStruct.toUpdateEventResponseDTO(updateEvent);

        return  ResponseEntity.ok(updateEventResponseDTO);
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userId = parseUserId(jwt);

        eventService.deleteEventForOrganizer(userId, eventId);

        return ResponseEntity.noContent().build();
    }
}
