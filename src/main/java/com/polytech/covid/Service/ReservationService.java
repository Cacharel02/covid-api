package com.polytech.covid.Service;

import java.util.List;
import org.springframework.stereotype.Service;

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

    public Reservation findReservationByName(String name){
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if(reservation.getPersonne().getName()==name){
                return reservation;
            }
        }
        return null;
    }

    public boolean hasbooked(Long centerId, String name){
        Center center = centerService.getById(centerId);
        Personne personne = personneService.findByName(name);
        List<Reservation> reservations = reservationRepository.findAllByCenterId(center.getId());
        Boolean hasbooked = false;
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
    
}
