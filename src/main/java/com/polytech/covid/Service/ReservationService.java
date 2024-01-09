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

    public Reservation createWithPerson(Personne personne){
        Reservation reservation = new Reservation();
        reservation.setPersonne(personne);
        return create(reservation);
    }

    public Reservation findReservationByName(Long centerId, String name) {
        if(hasbooked(centerId, name)){
            List<Reservation> reservations = centerService.getReservations(centerId);
            Boolean eq = reservations.contains(reservationRepository.findByPersonneName(name));
            if(eq){
                return reservationRepository.findByPersonneName(name);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public boolean hasbooked(Long centerId, String name){
        Personne personne = personneService.findByName(name);
        List<Reservation> reservations = centerService.getReservations(centerId);
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

    public boolean anyReservation(Personne personne){
        Boolean exist = false;
        for(Center center : centerService.getAllCenters()){
            if(hasbooked(center.getId(), personne.getName())){
                exist = true;
                break;
            }else{
                continue;
            }
        }
        return exist;
    }
    
    public void deleteByPatientId(Long patientId){
        Personne patient = personneService.findById(patientId);
        Reservation reservation = reservationRepository.findByPersonneName(patient.getName());
        centerService.deleteReservation(reservation);
        deleteById(reservation.getId());
    }
}
