package com.polytech.covid.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polytech.covid.Model.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin,Long>{
    SuperAdmin findByMail(String mail);
}
