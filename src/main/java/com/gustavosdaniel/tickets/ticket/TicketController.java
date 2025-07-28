package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.event.Event;
import com.gustavosdaniel.tickets.qrcode.QrCodeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final QrCodeService qrCodeService;

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

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {
        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(parseUserId(jwt), ticketId);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG); // Indica ao cliente que o corpo da resposta é uma imagem no formato JPEG
        headers.setContentLength(qrCodeImage.length); // Define o cabeçalho Content-Length com o tamanho em bytes da imagem

        return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    }
}
