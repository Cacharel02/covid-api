package com.polytech.covid.Service;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Model.Reservation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlobalService {

    private final ReservationService reservationService;

    private final CenterService centerService;

    private final VilleService villeService;

    private final PersonneService personneService;

    public List<Center> centers(){
        return centerService.findAll();
    }

    public List<Center> centersByTown(Long villeId){
        return villeService.getCenters(villeId);
    }

    public void book(Long centerId, Personne personne){
        if(!reservationService.anyReservation(personne)){
            Reservation reservation = reservationService.createWithPerson(personne);
            centerService.addReservation(centerId, reservation);
        }else{

        }
        
    }

    public void cancel(Long bookId){
        try {
            Reservation reservation = reservationService.findById(bookId);
            centerService.deleteReservation(reservation);
            reservationService.deleteById(bookId);
        } catch (NoSuchElementException e) {
            
        }
    }

    public void confirm(Long centerId, Long patientId){
        Personne personne = personneService.findById(patientId);
        if(reservationService.hasbooked(centerId, personne.getName())){
            Reservation reservation = reservationService.findReservationByName(centerId, personne.getName());
            personne.setIsVaccinated(true);
            cancel(reservation.getId());
        }else{

        }
    }
}
