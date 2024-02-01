package com.polytech.covid.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polytech.covid.Model.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long>{
    Ville findByName(String name);
}
