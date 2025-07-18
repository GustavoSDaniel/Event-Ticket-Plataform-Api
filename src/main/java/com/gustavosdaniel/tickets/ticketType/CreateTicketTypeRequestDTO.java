package com.gustavosdaniel.tickets.ticketType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotBlank(message = "Campo Obrigatório")
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private Integer totalAvailable;


    private String description;
}
