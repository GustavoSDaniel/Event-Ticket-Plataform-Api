package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class TicketsNotFoundException extends EventTicketException {

    public TicketsNotFoundException() {
    }

    public TicketsNotFoundException(String message) {
        super(message);
    }

    public TicketsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsNotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
