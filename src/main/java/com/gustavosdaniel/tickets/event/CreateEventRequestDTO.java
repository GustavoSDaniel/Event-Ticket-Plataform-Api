package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeRequest;
import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDTO {

    @NotBlank(message = "Campo nome é Obrigatório")
    private String name;

    @NotNull(message = "Campo de inicio de evento é obrigatório")
    @FutureOrPresent(message = "A data de inicio do evento não pode ser no passado")
    private LocalDateTime start;

    @NotNull(message = "Campo de termino de evento é obrigatório")
    @FutureOrPresent(message = "A data de termino do evento não pode ser no passado")
    private LocalDateTime end;

    @NotBlank(message = "Campo local é Obrigatório")
    private String venue;

    @NotNull(message = "Campo de inicio de vendas dos ingressos é obrigatório")
    @FutureOrPresent(message = "A data de inicio de vendas dos ingressos não pode ser no passado")
    private LocalDateTime salesStart;

    @NotNull(message = "Campo de fim das vendas dos ingressos é obrigatório")
    @FutureOrPresent(message = "A data do fim das vendas dos ingressos não pode ser no passado")
    private LocalDateTime salesEnd;

    @NotBlank(message = "Campo de status é Obrigatório")
    private EventStatusEnum status;

    @NotEmpty(message = "Pelo menos 1 tipo de ingresso é necessario")
    @Valid
    private List<CreateTicketTypeRequestDTO> ticketsTypes = new ArrayList<>();
}
