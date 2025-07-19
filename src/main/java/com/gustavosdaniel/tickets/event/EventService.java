package com.gustavosdaniel.tickets.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface EventService {

    Event createEvent(UUID organizedId,  CreateEventRequest CreateEventRequest);

}
