package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class EventUpdateNotFoundException extends EventTicketException {
    public EventUpdateNotFoundException() {
    }

    public EventUpdateNotFoundException(String message) {
        super(message);
    }

    public EventUpdateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventUpdateNotFoundException(Throwable cause) {
        super(cause);
    }

    public EventUpdateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
