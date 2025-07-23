package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.common.BaseEntity;
import com.gustavosdaniel.tickets.ticketType.TicketType;
import com.gustavosdaniel.tickets.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "event_start")
    private LocalDateTime startTime;

    @Column(name = "event_end")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String venue;

    @Column(name = "sales_start")
    private LocalDateTime salesStart;

    @Column(name = "sales_end")
    private LocalDateTime salesEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToMany(mappedBy = "attendingEvents")
    private List<User> attendees = new ArrayList<>();

    @ManyToMany(mappedBy = "staffingEvents")
    private List<User> staff = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true) // orphanRemoval = true caso eu tira esse tickettype ele sera excluido do banco
    private List<TicketType> ticketTypes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(venue, event.venue) && Objects.equals(salesStart, event.salesStart) && Objects.equals(salesEnd, event.salesEnd) && status == event.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(startTime);
        result = 31 * result + Objects.hashCode(endTime);
        result = 31 * result + Objects.hashCode(venue);
        result = 31 * result + Objects.hashCode(salesStart);
        result = 31 * result + Objects.hashCode(salesEnd);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
