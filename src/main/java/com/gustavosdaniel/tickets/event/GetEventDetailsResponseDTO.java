package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.GetEventTicketTypesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventDetailsResponseDTO {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<GetEventTicketTypesResponseDTO> getEventTicketTypesResponseDTOList = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
