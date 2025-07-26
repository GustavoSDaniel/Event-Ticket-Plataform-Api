package com.gustavosdaniel.tickets.ticket;

import com.gustavosdaniel.tickets.qrcode.QrCodeService;
import com.gustavosdaniel.tickets.ticketType.TicketType;
import com.gustavosdaniel.tickets.ticketType.TicketTypeNotFoundException;
import com.gustavosdaniel.tickets.ticketType.TicketTypeRepository;
import com.gustavosdaniel.tickets.user.User;
import com.gustavosdaniel.tickets.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException(String.format("User whit ID %s not found", userId)));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(()->new TicketTypeNotFoundException(
                        String.format("Ticket type whit ID %s not found", ticketTypeId)));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer toltalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 >  toltalAvailable) { // SE EU COMPRA MAIS 1 INGRESSO PASSARA A QUANTIDADE DISPONIVEL
            throw new TicketsSoldOutFoundException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PUBLISHED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);

        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);

    }
}
