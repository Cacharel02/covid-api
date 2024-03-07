package com.polytech.covid.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personne")
public class Personne extends User{

    @Column(name = "telephone")
    private String telephone;

    private Boolean isVaccinated;

    private Boolean isAuth;

}
