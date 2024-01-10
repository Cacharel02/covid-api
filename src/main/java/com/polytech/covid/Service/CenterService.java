package com.polytech.covid.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Admin;
import com.polytech.covid.Model.Center;
import com.polytech.covid.Model.Doctor;
import com.polytech.covid.Model.Reservation;
import com.polytech.covid.Repository.CenterRepository;
//import com.polytech.covid.Repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CenterService {
    
    
    private final CenterRepository centerRepository;

    private final VilleService villeService;

    private final AdminService adminService;

    private final DoctorService doctorService;

    //private final ReservationRepository reservationRepository;

    public void create(Center centre){
        centerRepository.save(centre);
    }

    public List<Center> findAll(){
        return centerRepository.findAll();
    }

    public List<Center> getCentersByTown(Long villeId){
        return villeService.getCenters(villeId);
    }

    public Center findById(Long id){
        return centerRepository.findById(id).get();
    }

    public Admin getAdmin(Long centerId){
        return findById(centerId).getAdmin();
    }

    public List<Reservation> reservations(Long centerId){
        return findById(centerId).getReservations();
    }

    public Center update(Long id, Center newCenter){
        return centerRepository.findById(id).map(center -> {
            if(newCenter.getName()!=null)center.setName(newCenter.getName());
            if(newCenter.getVille()!=null)center.setVille(newCenter.getVille());
            if(newCenter.getAdmin()!=null)center.setAdmin(newCenter.getAdmin());
            return centerRepository.save(center);
        }).orElseThrow();
    }

    public void delete(Long id){
        centerRepository.deleteById(id);
    }

    public Admin updateAdmin(Long id, Admin newAdmin){
        Admin admin = getAdmin(id);
        return adminService.update(admin.getId(), newAdmin);
    }

    public List<Doctor> getDoctors(Long centerId){
        return findById(centerId).getDoctors();
    }

    public Center addDoctor(Long centerId, Doctor doctor){
        return centerRepository.findById(centerId).map(center -> {
            List<Doctor> doctors = center.getDoctors();
            doctorService.save(doctor);
            doctors.add(doctor);
            center.setDoctors(doctors);
            return centerRepository.save(center);
        }).orElseThrow();
    }

    public Center deleteDoctor(Long centerId, Long doctorId){
        return centerRepository.findById(centerId).map(center -> {
            List<Doctor> doctors = center.getDoctors();
            doctors.remove(doctorService.getDoctorById(doctorId));
            center.setDoctors(doctors);
            doctorService.delete(doctorId);
            return centerRepository.save(center);
        }).orElseThrow();
    }

    public Center addReservation(Long centerId, Reservation reservation){
        return centerRepository.findById(centerId).map(center -> {
            List<Reservation> liste = center.getReservations();
            liste.add(reservation);
            center.setReservations(liste);
            return centerRepository.save(center);
        }).orElseThrow();
    }

    public Center findByReservation(Reservation reservation){
        Center c = null;
        for(Center center : centerRepository.findAll()){
            if(center.getReservations().contains(reservation)){
                c = center;
                break;
            }else{
                continue;
            }
        }
        return c;
        //retourner une exception si la réservation existe pas
    }

    public Center deleteReservation(Reservation reservation){
        return centerRepository.findById(findByReservation(reservation).getId()).map(center -> {
            List<Reservation> liste = center.getReservations();
            liste.remove(reservation);
            center.setReservations(liste);
            return centerRepository.save(center);
        }).orElseThrow();
    }
}
