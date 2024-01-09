package com.polytech.covid.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Service.CenterService;
import com.polytech.covid.Service.PersonneService;
import com.polytech.covid.Service.ReservationService;
import com.polytech.covid.Service.VilleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
    
    private final CenterService centerService;

    private final PersonneService personneService;

    private final ReservationService reservationService;

    private final VilleService villeService;

    @GetMapping("/centers")
    public List<Center> centers(){
        return centerService.getAllCenters();
    }

    @GetMapping("/{ville}/centers")
    public List<Center> centersByTown(@PathVariable("ville") Long id){
        return villeService.getCenters(id);
    }

    @PostMapping("/books")
    public Reservation book(@RequestParam(name = "center_id") Long id, @RequestBody Personne personne){
        if(!reservationService.anyReservation(personne)){
            Reservation reservation = new Reservation();
            personneService.create(personne);
            reservation.setPersonne(personne);
            reservationService.create(reservation);
            centerService.addReservation(id, reservation);
            return reservationService.create(reservation);
        }else{
            return null;
        }
    }

    @PutMapping("/cancel")
    public void cancel(@RequestParam(name = "patientId") Long id){
        reservationService.deleteByPatientId(id);
    }
}
