package com.gustavosdaniel.tickets.ticketValidation;

import com.gustavosdaniel.tickets.common.BaseEntity;
import com.gustavosdaniel.tickets.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ticket_validations")
public class TicketValidation extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketValidationStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "validation_method")
    private TicketValidationMethodEnum validationMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TicketValidation that = (TicketValidation) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
