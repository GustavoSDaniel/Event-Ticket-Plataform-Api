package com.gustavosdaniel.tickets.ticketType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateTicketTypeRequest {

    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;
}
