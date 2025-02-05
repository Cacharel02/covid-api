package com.polytech.covid.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polytech.covid.Model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
    
    Admin findByMail(String mail);
}
