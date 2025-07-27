package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.event.Event;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.gustavosdaniel.tickets.common.JwtUtil.parseUserId;

@RequestMapping(path = "/api/v1/tickets")
@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDTO>> listTickets(
            Pageable pageable,
            @AuthenticationPrincipal Jwt jwt
    ) {

        UUID userId = parseUserId(jwt);

        Page<Ticket> tickets = ticketService.listTicketsForUser(userId, pageable);


        return ResponseEntity.ok(tickets.map(ticketMapper::toListTicketResponseDTO));

    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDTO>  getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {
        return ticketService
                .getTicketForUser(parseUserId(jwt), ticketId)
                .map(ticketMapper::toGetTicketResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
