package com.polytech.covid.Service;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.polytech.covid.Exceptions.ExistingBook;
import com.polytech.covid.Exceptions.NoExistingAccount;
import com.polytech.covid.Exceptions.NoExistingBook;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Model.Ville;

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

    public void book(Long centerId, Personne personne) throws ExistingBook, NoExistingAccount{
        if(!reservationService.anyReservation(personne)){
            Reservation reservation = reservationService.createWithPerson(personne);
            centerService.addReservation(centerId, reservation);
        }else{
            throw new ExistingBook("Madame/Monsieur "+personne.getName()+" a déjà une réservation");
        }
    }

    public void cancel(Long bookId) throws NoExistingBook{
        try {
            Reservation reservation = reservationService.findById(bookId);
            centerService.deleteReservation(reservation);
            reservationService.deleteById(bookId);
        } catch (NoSuchElementException e) {
            throw new NoExistingBook("Cette réservation n'existe pas");
        }
    }

    public void confirm(Long centerId, Long patientId) throws NoExistingBook, NoExistingAccount{
        Personne personne = personneService.findById(patientId);
        if(reservationService.hasbooked(centerId, personne.getName())){
            Reservation reservation = reservationService.findReservationByName(centerId, personne.getName());
            personne.setIsVaccinated(true);
            cancel(reservation.getId());
        }else{

        }
    }

    public Personne getPatientById(Long id) {
        return personneService.findById(id);
    }

    public Ville findByName(String name){
        return villeService.getVilleByName(name);
    }
}
