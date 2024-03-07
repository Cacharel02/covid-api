package com.polytech.covid.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polytech.covid.Model.SuperAdmin;
import com.polytech.covid.Repository.SuperAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuperAdminService {
    @Autowired
    private SuperAdminRepository superAdminRepository;

    public SuperAdmin findByMail(String mail) {
        return superAdminRepository.findByMail(mail);
    }
    
}
