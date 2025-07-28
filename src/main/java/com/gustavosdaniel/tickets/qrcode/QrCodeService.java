package com.gustavosdaniel.tickets.qrcode;


import com.gustavosdaniel.tickets.ticket.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
