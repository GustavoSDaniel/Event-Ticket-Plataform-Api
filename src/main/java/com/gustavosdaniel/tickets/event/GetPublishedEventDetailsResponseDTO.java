package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.GetPublishedEventTicketTypesResponseDTO;
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
public class GetPublishedEventDetailsResponseDTO {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<GetPublishedEventTicketTypesResponseDTO> getPublishedEventTicketTypesResponseDTOSList = new ArrayList<>();

}
