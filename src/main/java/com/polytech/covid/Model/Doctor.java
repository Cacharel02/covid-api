package com.polytech.covid.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Entity
@Table(name = "medecin")
public class Doctor extends User{

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private static final Role role = Role.DOCTOR;
}
