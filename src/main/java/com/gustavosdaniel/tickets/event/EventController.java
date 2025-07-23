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
}
