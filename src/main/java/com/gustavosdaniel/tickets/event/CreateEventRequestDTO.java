package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.CreateTicketTypeRequest;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Campo Obrigatório")
    private String name;

    @FutureOrPresent
    private LocalDateTime start;

    @FutureOrPresent
    private LocalDateTime end;

    @NotBlank(message = "Campo Obrigatório")
    private String venue;

    @FutureOrPresent
    private LocalDateTime salesStart;

    @FutureOrPresent
    private LocalDateTime salesEnd;

    @NotBlank(message = "Campo Obrigatório")
    private EventStatusEnum status;

    @NotBlank(message = "Campo Obrigatório")
    private List<CreateTicketTypeRequest> ticketsTypes = new ArrayList<>();
}
