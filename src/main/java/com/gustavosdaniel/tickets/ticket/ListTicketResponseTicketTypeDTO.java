package com.gustavosdaniel.tickets.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseTicketTypeDTO {

    private UUID id;
    private String name;
    private BigDecimal price;

}
