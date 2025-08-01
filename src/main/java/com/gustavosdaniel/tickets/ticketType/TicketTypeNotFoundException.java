package com.gustavosdaniel.tickets.ticketType;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class TicketTypeNotFoundException extends EventTicketException {

    public TicketTypeNotFoundException() {
    }

    public TicketTypeNotFoundException(String message) {
        super(message);
    }

    public TicketTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
