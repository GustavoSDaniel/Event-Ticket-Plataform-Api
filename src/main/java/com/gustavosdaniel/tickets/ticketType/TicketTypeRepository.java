package com.gustavosdaniel.tickets.ticketType;

import com.gustavosdaniel.tickets.ticket.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {

    @Query("SELECT tt FROM TicketType tt WHERE tt.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE) // Previne que outros processos acessem o mesmo registro simultaneamente (que duas pessoas diferentes tentam comprar o mesmo ingfresso simultaneamente)
    Optional<TicketType> findByIdWithLock(@Param("id") UUID id);
}
