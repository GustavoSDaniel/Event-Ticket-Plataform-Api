package com.gustavosdaniel.tickets.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1/published-events")
@RestController
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> listPublishedEvents(Pageable pageable) {
        
    }

}
