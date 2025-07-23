package com.gustavosdaniel.tickets.ticketType;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class InvalidPriceException extends EventTicketException {
    public InvalidPriceException() {
    }

    public InvalidPriceException(String message) {
        super(message);
    }

    public InvalidPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceException(Throwable cause) {
        super(cause);
    }

    public InvalidPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
