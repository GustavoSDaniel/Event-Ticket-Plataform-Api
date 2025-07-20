package com.gustavosdaniel.tickets.ticketType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotBlank(message = "Campo nome é Obrigatório")
    private String name;

    private String description;

    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero (message = "O preço tem que ser maior ou igual a zero")
    private BigDecimal price;

    @NotNull(message = "Campo de quantidade de ingressos é obrigatório")
    @Positive(message = "Quantidade de ingressos não pode ser zero")
    private Integer totalAvailable;



}
