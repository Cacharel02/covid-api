package com.polytech.covid.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polytech.covid.Model.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long>{
    Personne findByName(String name);
}
