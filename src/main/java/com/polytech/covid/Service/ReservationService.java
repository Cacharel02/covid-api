package com.polytech.covid.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.polytech.covid.Exceptions.NoExistingAccount;
import com.polytech.covid.Exceptions.NoExistingBook;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final CenterService centerService;

    private final PersonneService personneService;

    public Reservation create(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long bookId){
        return reservationRepository.findById(bookId).get();
    }

    public Reservation createWithPerson(Personne personne, Reservation reservation){
        personneService.create(personne);
        reservation.setPersonne(personne);
        return create(reservation);
    }

    public Reservation findReservationByName(Long centerId, String name) throws NoExistingBook, NoExistingAccount {
        if(hasbooked(centerId, name)){
            return reservationRepository.findByPersonneName(name);
        }else{
            throw new NoExistingBook("Madame/Monsieur "+name+" n'a pas de réservation");
        }
    }

    public boolean hasbooked(Long centerId, String name) throws NoExistingAccount{
        Personne personne = personneService.findByName(name);
        Boolean hasbooked = false;
        if(personne == null){
            return hasbooked;
        }
        List<Reservation> reservations = centerService.reservations(centerId);
        for (Reservation reservation : reservations) {
            if(reservation.getPersonne().getId() == personne.getId()){
                hasbooked = true;
                break;
            }
            else{
                continue;
            }
        }
        
        return hasbooked;
    }

    public void deleteById(Long id){
        reservationRepository.deleteById(id);
    }

    public boolean anyReservation(Personne personne) throws NoExistingAccount{
        Boolean exist = false;
        for(Center center : centerService.findAll()){
            if(hasbooked(center.getId(), personne.getName())){
                exist = true;
                break;
            }else{
                continue;
            }
        }
        return exist;
    }
    
}
