package com.polytech.covid.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polytech.covid.Exceptions.IncorrectPassword;
import com.polytech.covid.Exceptions.NoExistingAccount;
import com.polytech.covid.Exceptions.NoExistingBook;
import com.polytech.covid.Model.Admin;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Doctor;
import com.polytech.covid.Model.LoginForm;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Service.CenterService;
import com.polytech.covid.Service.GlobalService;
import com.polytech.covid.Service.ReservationService;
import com.polytech.covid.Service.VilleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administration")
public class AdminController {
    
    private final CenterService centerService;

    private final VilleService villeService;

    private final ReservationService reservationService;

    private final GlobalService globalService;

    @PostMapping("/centers/create")
    public void create(@RequestBody Center centre){
        centerService.create(centre);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm login){

        try {
            return ResponseEntity.ok().body(globalService.login(login));
        } catch (NoExistingAccount e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IncorrectPassword e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/centers")
    public List<Center> centers(){
        return centerService.findAll();
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
        return centerService.getAdmin(id);
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
        return centerService.reservations(centerId);
    }

    //recherche d'une personne à son arrivée
    @GetMapping("/centers/{center}/books/find")
    public Reservation getReservationByName(@PathVariable("center") Long centerId, @RequestParam(name = "name") String name){
        try {
            return reservationService.findReservationByName(centerId, name);
        } catch (NoExistingBook | NoExistingAccount e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/centers/{center}/patients/{patient}/confirm")
    public void confirm(@PathVariable("center") Long centerId, @PathVariable("patient") Long patientId) throws Exception{//valider une vaccination
        globalService.confirm(centerId, patientId);
    }


}
