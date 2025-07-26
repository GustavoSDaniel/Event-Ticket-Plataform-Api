package com.gustavosdaniel.tickets.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1/published-events")
@RestController
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapStruct eventMapStruct;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> listPublishedEvents(
            @RequestParam(required = false) String q, //   @RequestParam(required = false) false, o método pode ser chamado sem o parâmetro
            Pageable pageable) {

        Page<Event> events;

        if(q != null && !q.trim().isEmpty()) {  // trim remove o espaços do começo e do final das frases

            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(events.map(eventMapStruct ::toListPublishedEventResponseDTO));

    }

}
