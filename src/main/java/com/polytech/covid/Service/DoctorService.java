package com.polytech.covid.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Doctor;
import com.polytech.covid.Repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    
    public Doctor save(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public void delete(Long id){
        doctorRepository.deleteById(id);
    }

    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id).get();
    }

    public Doctor findByMail(String mail){
        return doctorRepository.findByMail(mail);
    }
}
