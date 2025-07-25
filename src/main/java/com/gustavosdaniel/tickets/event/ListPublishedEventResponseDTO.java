package com.gustavosdaniel.tickets.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPublishedEventResponseDTO {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;


}
