package com.gustavosdaniel.tickets.user;

import com.gustavosdaniel.tickets.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User extends BaseEntity {

    private String name;
    private String email;



}
