package com.polytech.covid.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polytech.covid.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long>{
    
    Doctor findByMail(String mail);
}
