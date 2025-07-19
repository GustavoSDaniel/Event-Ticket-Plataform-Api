package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateEventResponseDTO {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<CreateTicketTypeResponseDTO> createTicketTypeResponseDTO;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
