package com.polytech.covid.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.polytech.covid.Model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Reservation findByPersonneName(String name);
}
