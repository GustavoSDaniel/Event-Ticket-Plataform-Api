package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class TicketNotNegativeException extends EventTicketException {
    public TicketNotNegativeException() {
    }

    public TicketNotNegativeException(String message) {
        super(message);
    }

    public TicketNotNegativeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketNotNegativeException(Throwable cause) {
        super(cause);
    }

    public TicketNotNegativeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
