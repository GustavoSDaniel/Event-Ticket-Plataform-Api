package com.gustavosdaniel.tickets.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("""
            SELECT e FROM Event e
                    WHERE e.organizer.id = :organizerId
        """)
    Page<Event> findByOrganizerId(@Param("organizerId") UUID organizerId, Pageable pageable);
}
