package com.gustavosdaniel.tickets.ticketType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventTicketTypesResponseDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
}
