package com.gustavosdaniel.tickets.qrcode;

import com.gustavosdaniel.tickets.common.EventTicketException;

public class QrCodeGenerationException extends EventTicketException {

    public QrCodeGenerationException() {
    }

    public QrCodeGenerationException(String message) {
        super(message);
    }

    public QrCodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodeGenerationException(Throwable cause) {
        super(cause);
    }

    public QrCodeGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
