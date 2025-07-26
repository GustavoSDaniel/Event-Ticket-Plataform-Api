package com.gustavosdaniel.tickets.qrcode;


import com.gustavosdaniel.tickets.ticket.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
