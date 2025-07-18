package com.gustavosdaniel.tickets.ticketType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequest {

    private String name;
    private BigDecimal price;
    private Integer totalAvailable;
    private String description;
}
