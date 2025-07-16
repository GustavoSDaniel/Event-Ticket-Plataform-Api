package com.gustavosdaniel.tickets.event;

import com.gustavosdaniel.tickets.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
}
