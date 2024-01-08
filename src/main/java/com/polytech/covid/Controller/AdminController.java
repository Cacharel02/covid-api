package com.polytech.covid.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polytech.covid.Model.Admin;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Doctor;
import com.polytech.covid.Model.Personne;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Service.CenterService;
import com.polytech.covid.Service.PersonneService;
import com.polytech.covid.Service.ReservationService;
import com.polytech.covid.Service.VilleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administration")
public class AdminController {
    
    private final CenterService centerService;

    private final VilleService villeService;

    private final PersonneService personneService;

    private final ReservationService reservationService;

    @PostMapping("/centers/create")
    public void create(@RequestBody Center centre){
        centerService.create(centre);
    }

    @GetMapping("/centers")
    public List<Center> centers(){
        return centerService.getAllCenters();
    }

    @GetMapping("/{ville}/centers")
    public List<Center> centersByTown(@PathVariable("ville") Long id){
        return villeService.getCenters(id);
    }

    @PutMapping("/centers/{id}/update")
    public Center update(@PathVariable("id") Long id, @RequestBody Center newCentre){
        return centerService.update(id, newCentre);
    }

    @DeleteMapping("/centers/{center}/delete")
    public void delete(@PathVariable("center") Long id){
        centerService.delete(id);
    }

    //gestion des administrateurs


    //admin d'un centre donné
    @GetMapping("/centers/{center}/admin")
    public Admin getAdmin(@PathVariable("center") Long id){
        return centerService.getAdminByCenterId(id);
    }

    @PutMapping("/centers/{center}/admin")
    public Admin updateAdmin(@PathVariable("center") Long centerId, @RequestBody Admin newAdmin){
        return centerService.updateAdmin(centerId, newAdmin);
    }

    //gestion des médecins
    @GetMapping("/centers/{center}/doctors")
    public List<Doctor> getDoctors(@PathVariable("center") Long centerId){
        return centerService.getDoctors(centerId);
    }

    @PutMapping("/centers/{center}/doctors")
    public Center addDoctor(@PathVariable("center") Long centerId, @RequestBody Doctor newMedecin){
        return centerService.addDoctor(centerId, newMedecin);
    }

    @PutMapping("/centers/{center}/doctors/{id}")
    public Center deleteDoctor(@PathVariable("center") Long centerId, @PathVariable("id") Long id){
        return centerService.deleteDoctor(centerId, id);
    }

    //gestion des réservations
    @GetMapping("/centers/{center}/books")
    public List<Reservation> getReservation(@PathVariable("center") Long centerId){
        return centerService.getReservations(centerId);
    }

    @GetMapping("/patients")
    public Personne getPersonne(@RequestParam("name") String name){
        return personneService.findByName(name);
    }

    @GetMapping("/centers/{center}/hasbooked")
    public String hasbooked(@PathVariable("center") Long centerId, @RequestParam("name") String name){
        if(reservationService.hasbooked(centerId, name)){
            return "La personne a une réservation";
        }else{
            return "La personne n'a pas de réservation";
        }
    }

    @PutMapping("/centers/{center}/patients/{patient}/confirm")
    public String confirm(@PathVariable("center") Long centerId, @PathVariable("patient") Long patientId){//valider une vaccination
        if (reservationService.hasbooked(centerId, personneService.findById(patientId).getName())) {
            personneService.setVaccination(patientId);
            centerService.deleteReservation(centerId, reservationService.findReservationByName(personneService.findById(patientId).getName()));
            reservationService.deleteById(reservationService.findReservationByName(personneService.findById(patientId).getName()).getId());
            return "Vaccination confirmée";
        }else{
            return "La personne n'a pas de réservation";
        }
    }
}
