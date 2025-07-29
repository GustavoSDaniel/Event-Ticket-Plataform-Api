package com.gustavosdaniel.tickets.ticketValidation;

import com.gustavosdaniel.tickets.qrcode.QrCode;
import com.gustavosdaniel.tickets.qrcode.QrCodeNotFoundException;
import com.gustavosdaniel.tickets.qrcode.QrCodeRepository;
import com.gustavosdaniel.tickets.qrcode.QrCodeStatusEnum;
import com.gustavosdaniel.tickets.ticket.Ticket;
import com.gustavosdaniel.tickets.ticket.TicketRepository;
import com.gustavosdaniel.tickets.ticketType.TicketTypeNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {

       QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
               .orElseThrow(() -> new QrCodeNotFoundException(
                String.format("QR Code with ID %s not found", qrCodeId)
        ));

        Ticket ticket = qrCode.getTicket(); // Obtém o ticket associado ao QR Code


        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);

    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum ticketValidationMethodEnum) {

        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethodEnum);

        TicketValidationStatusEnum ticketValidationStatusEnum =ticket.getTicketValidation()
                .stream()
                .filter(validation -> TicketValidationStatusEnum.VALID.equals(validation.getStatus())) // Verifica se o ticket já possui alguma validação com status VALID
                .findFirst()
                .map(validation -> TicketValidationStatusEnum.INVALID) // Se existir, marca a nova validação como INVALID (impede reutilização)
                .orElse(TicketValidationStatusEnum.VALID); // Se não existir, marca como VALID (primeira validação)

        ticketValidation.setStatus(ticketValidationStatusEnum);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketByManually(UUID ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketTypeNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
    }
}
