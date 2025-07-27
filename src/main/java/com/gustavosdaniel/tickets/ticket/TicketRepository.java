package com.gustavosdaniel.tickets.ticket;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByTicketTypeId(UUID ticketTypeId);

    @Query("""
            SELECT t FROM Ticket t WHERE t.purchaser.id = :purchaserId
        """)
    Page<Ticket> findByPurchaserId(@Param("purchaserId") UUID purchaserId, Pageable pageable);


}
