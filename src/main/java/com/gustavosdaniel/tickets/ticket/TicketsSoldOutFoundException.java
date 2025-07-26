package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class TicketsSoldOutFoundException extends EventTicketException {

    public TicketsSoldOutFoundException() {
    }

    public TicketsSoldOutFoundException(String message) {
        super(message);
    }

    public TicketsSoldOutFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsSoldOutFoundException(Throwable cause) {
        super(cause);
    }

    public TicketsSoldOutFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
