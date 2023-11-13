package com.polytech.covid.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polytech.covid.Model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    
    List<Reservation> findAllByCenterId(Long id);
}
