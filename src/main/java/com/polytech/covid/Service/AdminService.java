package com.polytech.covid.Service;

import org.springframework.stereotype.Service;

import com.polytech.covid.Model.Admin;
import com.polytech.covid.Model.Role;
import com.polytech.covid.Repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminRepository adminRepository;

    public Admin save(Admin admin){
        admin.setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }

    public Admin getById(Long id){
        return adminRepository.findById(id).get();
    }

    public Admin update(Long id, Admin newAdmin){
        return adminRepository.findById(id).map(admin -> {
            admin.setName(newAdmin.getName());
            return adminRepository.save(admin);
        }).orElseThrow();
    }

    public Admin findByMail(String mail){
        return adminRepository.findByMail(mail);
    }
}
