package com.gustavosdaniel.tickets.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("""
            SELECT e FROM Event e
                    WHERE e.organizer.id = :organizerId
        """)
    Page<Event> findByOrganizerId(@Param("organizerId") UUID organizerId, Pageable pageable);

    @Query("""
            SELECT e FROM Event e 
                    WHERE e.id = :id AND e.organizer = :organizerId
        """)
    Optional<Event> findByIdAndOrganizerId(@Param("id") UUID id, @Param("organizerId") UUID organizerId);

    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);
}
