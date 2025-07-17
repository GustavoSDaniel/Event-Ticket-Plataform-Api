package com.gustavosdaniel.tickets.qrcode;

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
public class QrCode extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QrCodeStatusEnum status;

    @Column(nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        QrCode qrCode = (QrCode) o;
        return status == qrCode.status && Objects.equals(value, qrCode.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(value);
        return result;
    }
}
