package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.ticketType.TicketType;
import com.gustavosdaniel.tickets.user.User;
import com.gustavosdaniel.tickets.user.UserNotFoundException;
import com.gustavosdaniel.tickets.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public final Event createEvent(UUID organizedId, CreateEventRequest createEventRequest) {

        User organized = userRepository.findById(organizedId)
                .orElseThrow(() -> new UserNotFoundException(
                            String.format("User with ID '%s' not found", organizedId )));

        List<TicketType> ticketTypesToCreate = createEventRequest.getTicketsTypes().stream()
                .map(requestTicketType -> {

                    if (requestTicketType.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                        throw new InvalidPriceException(
                                "O preço do ingresso '"
                                        + requestTicketType.getName()
                                        + " não pode ser menor que zero."
                        );
                    }
                    return TicketType.builder()
                            .name(requestTicketType.getName())
                            .price(requestTicketType.getPrice())
                            .description(requestTicketType.getDescription())
                            .totalAvailable(requestTicketType.getTotalAvailable())
                            .build();
                })
                .collect(Collectors.toList());

        Event eventToCreate = new Event();
        eventToCreate.setName(eventToCreate.getName());
        eventToCreate.setStartTime(eventToCreate.getStartTime());
        eventToCreate.setEndTime(eventToCreate.getEndTime());
        eventToCreate.setVenue(eventToCreate.getVenue());
        eventToCreate.setSalesStart(eventToCreate.getSalesStart());
        eventToCreate.setSalesEnd(eventToCreate.getSalesEnd());
        eventToCreate.setStatus(eventToCreate.getStatus());
        eventToCreate.setOrganizer(organized);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);

    }
}
